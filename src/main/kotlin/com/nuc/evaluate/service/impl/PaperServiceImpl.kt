package com.nuc.evaluate.service.impl


import com.nuc.evaluate.entity.result.Json
import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.po.ClassAndPages
import com.nuc.evaluate.po.StudentAnswer
import com.nuc.evaluate.po.Title
import com.nuc.evaluate.repository.*
import com.nuc.evaluate.service.PaperService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
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
    private lateinit var studentAnswerRepository: StudentAnswerRepository

    /**
     *
     */
    override fun listClassPage(classId: Long): List<ClassAndPages> {
        val pages = classAndPagesRepository.findByClassId(classId)
        if (pages.isEmpty()) {
            throw ResultException("没有该班级", 500)
        }
        return pages
    }

    /**
     *
     */
    @Transactional
    override fun getOnePage(pageId: Long, classId: Long): List<Title> {
        val classAndPages = classAndPagesRepository.findByPagesIdAndClassId(pageId, classId).toList()
        if (classAndPages.isEmpty()) {
            throw ResultException("没有该考试", 500)
        }

        val nowTime = Timestamp(System.currentTimeMillis())
        if (nowTime.after(classAndPages[0].endTime) || nowTime.before(classAndPages[0].startTime)) {
            throw ResultException("该时间段内没有该考试", 500)
        }
        val pagesAndTitleList = pagesAndTitleRepository.findByPagesId(pageId)
        return pagesAndTitleList.map {
            titleRepository.findOne(it.titleId)
        }
    }

    /**
     * 进行判题
     */
    @RabbitListener(queues = ["fanout.check"])
    @RabbitHandler
    fun addPages(result: Json) {
        logger.info("Receiver check: $result")
        Thread.sleep(5000)

    }

    /**
     * 将学生答案写入数据库
     */
    @RabbitListener(queues = ["fanout.add"])
    @RabbitHandler
    @Transactional
    fun addAnswer(result: Json) {
        logger.info("Receiver add: $result")
        val ansList = ArrayList<StudentAnswer>()
        for (i in result.result.answer) {
            val studentAnswer = StudentAnswer()
            studentAnswer.titleId = i.id
            studentAnswer.answer = i.ans
            studentAnswer.time = Timestamp(System.currentTimeMillis())
            studentAnswer.studentId = result.result.studentId
            studentAnswer.pagesId = result.result.pageId
            ansList.add(studentAnswer)
        }
        ansList.map {
            studentAnswerRepository.save(it)
        }
    }

    override fun verifyPage(result: Json) {
        val ansSet = studentAnswerRepository.findByStudentIdAndPagesId(result.result.studentId,
                result.result.pageId)
        if (ansSet.isNotEmpty()) {
            throw ResultException("你已经提交过答案，请勿重复提交", 500)
        }
    }
}