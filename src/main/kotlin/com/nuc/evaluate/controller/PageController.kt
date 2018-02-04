package com.nuc.evaluate.controller


import com.nuc.evaluate.po.Title
import com.nuc.evaluate.result.Result
import com.nuc.evaluate.service.PaperService
import com.nuc.evaluate.util.ResultUtils
import com.nuc.evaluate.vo.PageVO
import com.nuc.evaluate.vo.TitleVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2018/2/3 11:22
 * @version 1.0
 */
@RestController
@RequestMapping("/page")
class PageController {

    @Autowired
    private lateinit var paperService: PaperService

    /**
     * 通过 `classId` 获取该班级所有的考试
     * @param classId 班级id
     */
    @GetMapping("/listPagesByClassId")
    fun listPages(classId: Long): Result {
        return ResultUtils.success(200, "查询成功", paperService.listClassPage(classId))
    }

    /**
     * 通过 `pageId` 获取考试试题
     * @param pageId 试卷id
     */
    @GetMapping("/onePage")
    fun getPage(pageId: Long): Result {
        val title = paperService.getOnePage(pageId)
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