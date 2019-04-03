package com.nuc.libra.controller


import com.alibaba.fastjson.JSON
import com.nuc.libra.exception.ResultException
import com.nuc.libra.result.Result
import com.nuc.libra.service.PaperService
import com.nuc.libra.util.CompilerUtils
import com.nuc.libra.util.ResultUtils
import com.nuc.libra.vo.ExamParam
import com.nuc.libra.vo.PageVO
import com.nuc.libra.vo.TitleVO
import com.nuc.libra.vo.VerifyPageParam
import io.swagger.annotations.ApiOperation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * @author 杨晓辉 2018/2/3 11:22
 * 试卷管理
 */
@RestController
@RequestMapping("/page")
class PageController {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var paperService: PaperService

    @Autowired
    private lateinit var rabbitTemplate: AmqpTemplate

    /**
     * 通过 `classId` 获取该班级所有的考试
     * @param classId 班级 `id`
     */
    @GetMapping("/exams/{classId}")
    fun listPages(
        @PathVariable(name = "classId") classId: Long
    ): Result {
        return ResultUtils.success(200, "查询成功", paperService.listClassPage(classId))
    }

    /**
     * 通过 `pageId` `class id`获取考试试题
     * @param examParam 试卷信息
     */
    @GetMapping("/exam")
    fun getPage(examParam: ExamParam): Result {

        val titleList = paperService.getOnePage(examParam.classId, examParam.pageId)

        val titleVOList: MutableList<TitleVO> = ArrayList()
        titleList.forEach {
            val titleVO = TitleVO()
            BeanUtils.copyProperties(it, titleVO)
            var blankNumber = 0
            if (it.category == "2") {
                val sb = StringBuilder()
                val titles = it.title.split("_{0,15}_".toRegex())
                for (i in 0 until titles.size - 1) {
                    sb.append(titles[i])
                    sb.append("_____")
                }
                sb.append(titles.last())
                it.title = sb.toString().trim()
                blankNumber = titles.size - 1
            }
            titleVO.blankNum = blankNumber
            titleVOList.add(titleVO)
        }

        val pageVO = PageVO()
        /*
         * 题的类型：1单选2填空3简答4程序5算法试题
         */
        titleVOList.forEach {
            when (it.category) {
                "1" -> pageVO.signChoice.add(it)
                "2" -> pageVO.blank.add(it)
                "3" -> pageVO.ansQuestion.add(it)
                "4" -> pageVO.codeQuestion.add(it)
                "5" -> pageVO.algorithm.add(it)
                else -> {
                }
            }
        }
        return ResultUtils.success(200, "获取成功", pageVO)
    }

    /**
     * 上传考试答案
     *
     * 按照下面的格式进行试题返回
     *
     * ``` javascript
     * let result = {
     *  studentId: 1,
     *  pageId: 1,
     *  answer: [{
     *      id: '1',
     *      ans: 'A'
     *  },
     *  {
     *      id: '37',
     *      ans: '&amp;nbsp;'
     *  },
     *  {
     *      id: '1024',
     *      ans: "（1） Grinder. 可视化的图形界面可以监控丰富的资源；报告可以导出到Word、Excel以及HTML格式。"
     *  },
     *  ]
     * }
     * ```
     */
    @PostMapping("/addAns")
    fun addAns(@RequestBody json: String): Result {
        logger.info("answer json is $json")
        val result = JSON.parseObject(json, com.nuc.libra.entity.result.Result::class.java)
                ?: throw ResultException("解析错误", 500)
//        paperService.verifyPage(result.result.studentId, result.result.pageId)
        logger.info("result is  $result")
        rabbitTemplate.convertAndSend("check", result)

        return ResultUtils.success(message = "提交成功")
    }

    /**
     * 获取一个学生所有考试分数
     * @param studentId 学生id
     */
    @GetMapping("/scores/{studentId}")
    fun listScore(@PathVariable(name = "studentId") studentId: Long): Result {
        return ResultUtils.success(
            200, "获取成功", paperService.listScore(studentId)
        )
    }

    /**
     * 获取单项考试分数
     * @param pageId 试卷id
     * @param studentId 学生id
     */
    @GetMapping("/Score/{pageId}/{studentId}")
    fun getOneScore(@PathVariable(name = "pageId") pageId: Long, @PathVariable(name = "studentId") studentId: Long): Result {
        return ResultUtils.success(200, "获取成功", paperService.getPageScore(pageId, studentId))
    }

    /**
     * 试卷验证
     * @param verifyPageParams 校验参数
     */
    @PostMapping("/verifyPage")
    fun verifyPage(@RequestBody verifyPageParams: VerifyPageParam): Result {

        return ResultUtils.success(
            200,
            "未考试",
            paperService.verifyPage(verifyPageParams.studentId, verifyPageParams.pageId)
        )

    }

    /**
     * 编译代码测试
     * @Test 测试接口
     */
    @ApiOperation(
        value = "用户运行编写代码", notes = "public class Hello {\n" +
                "    private int age;\n" +
                "\n" +
                "    public void setAge(int var1) {\n" +
                "        this.age = var1;\n" +
                "    }\n" +
                "\n" +
                "    public int getAge() {\n" +
                "        return this.age;\n" +
                "    }\n" +
                "}"
    )
    @PostMapping("/runCode")
    fun runCode(@RequestBody code: String): Result {
        println("code is $code")
        val className = code.substringAfter("public class").substringBefore("{").trim()

        return ResultUtils.success(200, "编译完成", CompilerUtils.buildTargetSource(code, className))
    }

}