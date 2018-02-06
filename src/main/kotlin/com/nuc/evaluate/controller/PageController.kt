package com.nuc.evaluate.controller


import com.alibaba.fastjson.JSON
import com.nuc.evaluate.entity.EmailMessage
import com.nuc.evaluate.entity.result.Json
import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.po.Title
import com.nuc.evaluate.result.Result
import com.nuc.evaluate.service.PaperService
import com.nuc.evaluate.util.ResultUtils
import com.nuc.evaluate.vo.PageVO
import com.nuc.evaluate.vo.TitleVO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

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
    fun listPages(classId: Long): Result {
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
     *
     * let result = {
     *  studentId: 1,
     *  pageId: 1,
     *  signChoice: [{
     *      id: 1,
     *      answer: 'A'
     *  }],
     *  multipleChoice: [{
     *      id: 2,
     *      answer: "A,B"
     *  } // 采用逗号分割
     *  ],
     *  trueOrFalse: [{
     *      id: 3,
     *      answer: "true"
     *  }],
     *  blank: [{
     *      id: 4,
     *      answer: "【这是第一空答案】【这是第二空答案】"
     *  }],
     *  code: [{
     *      id: 5,
     *      answer: 'println("Hello World")'
     *  }],
     *  ans: [{
     *      id: 6,
     *      answer: "这是问答题答案"
     *  }],
     *  draw: [{
     *      id: 7,
     *      answer: "画图题答案"
     *  }]
     * }
     *
     *
     * ```
     */
    @PostMapping("/addAns")
    fun addAns(@RequestBody json: String): Result {
        logger.info("json is $json")
        val result = JSON.parseObject(json, Json::class.java)
                ?: throw ResultException("解析错误", 500)
        logger.info("result is  $result")
        rabbitTemplate.convertAndSend("page", result)

        return ResultUtils.success()
    }

    /**
     * 获取历史试卷
     * todo(2/5日之后补充)
     */
    @GetMapping("/getHistoryPage")
    fun getHistoryPage(): Result {
        return ResultUtils.success()
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
        titleVO.setSection(titleVO.category)
        return titleVO
    }

}