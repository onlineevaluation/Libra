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
import org.springframework.validation.BindingResult
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
        classId: Long,
        bindingResult: BindingResult
    ): Result {
        if (bindingResult.hasErrors()) {
            throw ResultException(bindingResult.fieldError.defaultMessage, 500)
        }

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
     *      ans: "（1） Grinder. 是一个开源的JVM负载测试框架，它通过很多负载注射器来为分布式测试提供了便利。支持用于执行测试脚本的Jython脚本引擎.HTTP测试可通过HTTP代理进行管理。根据项目网站的说法，Grinder的主要目标用户是“理解他们所测代码的人——Grinder不仅仅是带有一组相关响应时间的‘黑盒’测试。由于测试过程可以进行编码——而不是简单地脚本化，所以程序员能测试应用中内部的各个层次，而不仅仅是通过用户界面测试响应时间。（2）fwptt。也是一个用来进行Web应用负载测试的工具。它可以记录一般的请求，也可以记录Ajax请求。它可以用来测试ASP.NET，JSP，PHP或是其它的Web应用.（3）LoadRunner   支持多种常用协议多且个别协议支持的版本比较高；可以设置灵活的负载压力测试方案，可视化的图形界面可以监控丰富的资源；报告可以导出到Word、Excel以及HTML格式。"
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
    fun getOneScore(studentId: Long,pageId: Long) {

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