package com.nuc.evaluate.controller


import com.nuc.evaluate.po.Page
import com.nuc.evaluate.po.Title
import com.nuc.evaluate.result.Result
import com.nuc.evaluate.service.PaperService
import com.nuc.evaluate.util.ResultUtils
import com.nuc.evaluate.vo.TitleVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2018/2/3 11:22
 */
@RestController
@RequestMapping("/page")
class PageController {

    @Autowired
    private lateinit var paperService: PaperService

    @GetMapping("/listPagesByClassId")
    fun listPages(classId: Long): Result {
        return ResultUtils.success(200, "查询成功", paperService.listClassPage(classId))
    }

    @GetMapping("/onePage")
    fun getPage(pageId: Long): Result {
        val title = paperService.getOnePage(pageId)
        val titleVOList: MutableList<TitleVO> = ArrayList()
        title.map {
            titleVOList.add(po2vo(it))
        }
        return ResultUtils.success(200, "获取成功", titleVOList)
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