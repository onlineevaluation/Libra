package com.nuc.libra.controller


import com.alibaba.fastjson.JSON
import com.nuc.libra.exception.ResultException
import com.nuc.libra.result.Result
import com.nuc.libra.service.PaperService
import com.nuc.libra.util.CompilerUtils
import com.nuc.libra.util.ResultUtils
import com.nuc.libra.vo.*
import io.swagger.annotations.ApiOperation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

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
    @GetMapping("/exam/{classAndPageId}")
    fun getPage(@PathVariable(name = "classAndPageId") classAndPageId: Long): Result {
        val pageVO = paperService.getOnePage(classAndPageId)
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
     *  doTime:100
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
        val result = JSON.parseObject(json, com.nuc.libra.vo.Result::class.java)
                ?: throw ResultException("解析错误", 500)
        paperService.verifyPage(result.studentId, result.pageId)
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