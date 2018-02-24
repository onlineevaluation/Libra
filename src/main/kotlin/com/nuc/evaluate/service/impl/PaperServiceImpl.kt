package com.nuc.evaluate.service.impl


import com.nuc.evaluate.entity.result.Json
import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.po.ClassAndPages
import com.nuc.evaluate.po.StudentAnswer
import com.nuc.evaluate.po.StudentScore
import com.nuc.evaluate.po.Title
import com.nuc.evaluate.repository.*
import com.nuc.evaluate.service.PaperService
import com.nuc.evaluate.util.WordUtils
import com.nuc.evaluate.vo.AnsVO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Timestamp
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

    @Autowired
    private lateinit var studentScoreRepository: StudentScoreRepository

    /**
     * 获取该班级的所有考试
     * @param classId 班级id
     * @return list 班级考试试卷
     */
    override fun listClassPage(classId: Long): List<ClassAndPages> {
        val pages = classAndPagesRepository.findByClassId(classId)
        if (pages.isEmpty()) {
            throw ResultException("没有该班级", 500)
        }
        return pages
    }

    /**
     * 获取指定考试
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
     *
     * 通过 监听rabbitMQ， 进行判题
     * @param json 前端字符串
     */
    @Transactional
    @RabbitListener(queues = ["check"])
    @RabbitHandler
    fun addPages(result: Json) {

        val ansList = ArrayList<StudentAnswer>()

        result.result.answer.map {
            val title = titleRepository.findOne(it.id) ?: throw ResultException("该试题不存在", 500)
            val studentAnswer = StudentAnswer()
            studentAnswer.pagesId = result.result.pageId
            studentAnswer.studentId = result.result.studentId
            studentAnswer.time = Timestamp(System.currentTimeMillis())
            studentAnswer.score = 0.0
            studentAnswer.answer = it.ans
            studentAnswer.titleId = it.id
            when (title.category) {
            // 单选题
                "0" -> {
                    logger.info("单选题")

                    if (it.ans.toUpperCase() == title.answer?.toUpperCase()) {
                        studentAnswer.score = title.score
                    }
                    ansList.add(studentAnswer)
                }
            // 多选题
                "1" -> {

                }
            // 判断题
                "2" -> {

                }
            // 填空题
                "3" -> {
                    logger.info("填空题")
                    val blankContentList = it.ans.split("】".toRegex())
                    logger.info("blankContentList: ${blankContentList[0]}")
                    blankContentList.map {
                        val similarScore = WordUtils.blankCheck(it.substringAfter("【"), title.answer!!)
                        val score = when (similarScore) {
                            in 0.0..0.75 -> {
                                0.0
                            }
                            in 0.75..1.0 -> {
                                title.score
                            }
                            else -> {
                                0.0
                            }
                        }
                        studentAnswer.score += score
                    }
                    ansList.add(studentAnswer)
                }
            // 简答题
                "6" -> {
                    logger.info("解答题")
                    val similarScore = WordUtils.ansCheck(it.ans, title.answer!!)
                    val score: Double = when (similarScore) {
                        in 0.0..0.25 -> {
                            0.20 * title.score
                        }
                        in 0.25..0.5 -> {
                            0.50 * title.score
                        }
                        in 0.5..0.75 -> {
                            0.80  * title.score
                        }
                        in 0.75..1.0 -> {
                            1.0 * title.score
                        }
                        else -> {
                            0.0
                        }
                    }
                    studentAnswer.score = score
                    ansList.add(studentAnswer)
                }

                else -> {
                    logger.info("else")
                    ansList.add(studentAnswer)
                }
            }

        }
        studentAnswerRepository.save(ansList)
        val scoreList = studentAnswerRepository.findByStudentIdAndPagesId(result.result.studentId, result.result.pageId)
        var sumScore = 0.0
        scoreList.map {
            sumScore += it.score
        }
        val studentScore = StudentScore()
        studentScore.pagesId = result.result.pageId
        studentScore.studentId = result.result.studentId
        studentScore.status = "2"
        studentScore.score = sumScore
        studentScore.time = Timestamp(System.currentTimeMillis())
        studentScore.dotime = Date(System.currentTimeMillis())
        studentScoreRepository.save(studentScore)
    }

    /**
     * 试卷校验
     */
    override fun verifyPage(result: Json) {
        val ansList = studentAnswerRepository.findByStudentIdAndPagesId(
            result.result.studentId,
            result.result.pageId
        )
        if (ansList.isNotEmpty()) {
            throw ResultException("你已经提交过答案，请勿重复提交", 500)
        }
    }


    /**
     * 获取所有成绩
     */
    override fun listScore(studentId: Long): List<StudentScore> {
        return studentScoreRepository.findByStudentId(studentId)
                ?: throw ResultException("你还没有参加考试", 500)
    }

    /**
     * 查看单张试卷考试成绩
     *
     * 今天是戊戌年大年初二
     * @param pageId 试卷id
     * @param studentId 学生id
     *
     * @return ansVO 返回 ansVo 对象
     */
    @Transactional
    override fun getPageScore(pageId: Long, studentId: Long): AnsVO {
        val ansVO = AnsVO()
        val studentScore = studentScoreRepository.findByPagesIdAndStudentId(pageId, studentId)
                ?: throw ResultException("没有该成绩", 500)
        ansVO.ansList = studentAnswerRepository.findByStudentIdAndPagesId(studentId, pageId) as ArrayList<StudentAnswer>
        ansVO.pageId = pageId
        ansVO.score = studentScore.score
        return ansVO
    }

    /**
     * 进行试题收藏
     */


}