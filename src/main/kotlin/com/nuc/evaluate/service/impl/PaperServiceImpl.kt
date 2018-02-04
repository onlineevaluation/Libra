package com.nuc.evaluate.service.impl

import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.po.ClassAndPages
import com.nuc.evaluate.po.Title
import com.nuc.evaluate.repository.*
import com.nuc.evaluate.service.PaperService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*
import javax.transaction.Transactional

/**
 * @author 杨晓辉 2018/2/3 16:04
 */
@Service
class PaperServiceImpl : PaperService {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var classAndPagesRepository: ClassAndPagesRepository

    @Autowired
    private lateinit var pagesRepository: PagesRepository

    @Autowired
    private lateinit var pagesAndTitleRepository: PageAndTitleRepository

    @Autowired
    private lateinit var titleRepository: TitleRepository

    @Autowired
    private lateinit var classAndExamRepository: ClassAndExamRepository

    override fun listClassPage(classId: Long): List<ClassAndPages> {
        val pages = classAndPagesRepository.findByClassId(classId)
        if (pages.isEmpty()) {
            throw ResultException("没有该班级", 500)
        }
        return pages
    }

    @Transactional
    override fun getOnePage(pageId: Long, classId: Long): List<Title> {
//        pagesRepository.findOne(pageId) ?: throw ResultException("没有该考试", 500)
        //val classAndPages = classAndPagesRepository.findByPagesIdAnd(pageId)
        val classAndExam = classAndExamRepository.findByPaperIdAndClassId(pageId, classId)
                ?: throw ResultException("没有该考试", 500)
        val nowTime = Timestamp(System.currentTimeMillis())
        if (nowTime.after(classAndExam.endTime) || nowTime.before(classAndExam.startTime)) {
            throw ResultException("该时间段内没有该考试", 500)
        }
        println("date is $nowTime")
        val pagesAndTitleList = pagesAndTitleRepository.findByPagesId(pageId)
        return pagesAndTitleList.map {
            titleRepository.findOne(it.titleId)
        }
    }


}