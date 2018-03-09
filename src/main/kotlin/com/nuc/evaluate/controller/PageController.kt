package com.nuc.evaluate.controller


import com.alibaba.fastjson.JSON
import com.nuc.evaluate.entity.result.Json
import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.po.Title
import com.nuc.evaluate.result.Result
import com.nuc.evaluate.service.PaperService
import com.nuc.evaluate.util.ResultUtils
import com.nuc.evaluate.vo.PageVO
import com.nuc.evaluate.vo.TitleVO
import org.hibernate.validator.constraints.NotEmpty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * @author 杨晓辉 2018/2/3 11:22
 * @version 1.0
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
    @GetMapping("/listPagesByClassId")
    fun listPages(
        @Valid
        @NotEmpty(message = "班级不能为空")
        @NotNull(message = "班级不能为空")
        @Min(value = 0L, message = "班级不能为0")
        classId: Long
    ): Result {
        return ResultUtils.success(200, "查询成功", paperService.listClassPage(classId))
    }

    /**
     * 通过 `pageId` 获取考试试题
     * @param pageId 试卷 `id`
     */
    @GetMapping("/onePage")
    fun getPage(pageId: Long, classId: Long): Result {
        val title = paperService.getOnePage(pageId, classId)
        val titleVOList: MutableList<TitleVO> = ArrayList()
        title.map {
            titleVOList.add(po2vo(it))
        }

        val pageVO = PageVO()
        /*
         * 题的类型：0单选1多选2判断3填空4程序5画图6简答
         */
        titleVOList.map {
            when (it.category) {
                "0" -> pageVO.signChoice.add(it)
                "1" -> pageVO.multipleChoice.add(it)
                "2" -> pageVO.trueOrFalse.add(it)
                "3" -> pageVO.blank.add(it)
                "4" -> pageVO.codeQuestion.add(it)
                "5" -> pageVO.drawingQuestion.add(it)
                "6" -> pageVO.ansQuestion.add(it)
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
        logger.info("json is $json")
        val result = JSON.parseObject(json, Json::class.java)
                ?: throw ResultException("解析错误", 500)
        paperService.verifyPage(result)
        logger.info("result is  $result")
        rabbitTemplate.convertAndSend("check", result)

        return ResultUtils.success("提交成功")
    }

    /**
     * 获取所有考试分数
     */
    @GetMapping("/listScore")
    fun listScore(studentId: Long): Result {
        return ResultUtils.success(
            200, "获取成功", paperService.listScore(studentId)
        )
    }

    /**
     * 获取单项考试分数
     */
    @GetMapping("/getScore")
    fun getOneScore(pageId: Long, studentId: Long): Result {
        return ResultUtils.success(200, "获取成功", paperService.getPageScore(pageId, studentId))
    }

    /**
     * po --> vo
     */
    private fun po2vo(title: Title): TitleVO {
        val titleVO = TitleVO()
        titleVO.id = title.id
        titleVO.category = title.category
        titleVO.title = title.title
        titleVO.difficulty = title.difficulty
        titleVO.num = title.num
        titleVO.score = title.score
        titleVO.completeTime = title.completeTime

        when (title.category) {
        // 单选题和多选题
            "0", "1" -> {
                titleVO.sectionA = title.sectiona
                titleVO.sectionB = title.sectionb
                titleVO.sectionC = title.sectionc
                titleVO.sectionD = title.sectiond
            }
        // 填空题
            "3" -> {
                val sb = StringBuilder()
                val titleList = title.title.split("_{0,15}_".toRegex())
                for (i in 0 until titleList.size - 1) {
                    sb.append(titleList[i])
                    sb.append("【 】")
                }
                sb.append(titleList.last())
                titleVO.title = sb.toString().trim()

                titleVO.blankNum = titleList.size - 1
            }

            else -> {
            }
        }
        return titleVO
    }


}