package com.nuc.libra.service.impl


import com.nuc.libra.entity.result.Json
import com.nuc.libra.exception.ResultException
import com.nuc.libra.po.ClassAndPages
import com.nuc.libra.po.StudentAnswer
import com.nuc.libra.po.StudentScore
import com.nuc.libra.po.Title
import com.nuc.libra.repository.*
import com.nuc.libra.service.PaperService
import com.nuc.libra.util.WordUtils
import com.nuc.libra.vo.AnsVO
import com.nuc.libra.vo.StudentAnswerSelect
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
 * @Version 1.0
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
    private lateinit var studentAnswerRepository: StudentAnswerRepository

    @Autowired
    private lateinit var studentScoreRepository: StudentScoreRepository

    @Autowired
    private lateinit var titleRepository: TitleRepository


    /**
     * 获取该班级的所有考试
     * @param classId 班级id
     * @return list 班级考试试卷
     */
    override fun listClassPage(classId: Long): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        val classAndPages = classAndPagesRepository.findByClassId(classId)
        if (classAndPages.isEmpty()) {
            throw ResultException("没有该班级/该班级没有考试", 500)
        }
        classAndPages.forEach {
            logger.info("page id is ${it.pagesId}")
            val page = pagesRepository.findById(it.pagesId).get()
            map["exam"] = it
            map["pageTitle"] = page.name
        }

        return map
    }

    /**
     * 获取指定考试的试题
     * @param pageId 试卷id
     * @param classId 班级id
     * @return 返回试卷试题
     */
    @Transactional
    override fun getOnePage(classId: Long, pageId: Long): List<Title> {

        logger.info("classId :: $classId & pageId :: $pageId")

        val classAndPages = classAndPagesRepository.findByPagesIdAndClassId(pageId, classId).toList()
        if (classAndPages.isEmpty()) {
            throw ResultException("没有该考试", 500)
        }

        val nowTime = Timestamp(System.currentTimeMillis())
        if (nowTime.after(classAndPages[0].endTime) || nowTime.before(classAndPages[0].startTime)) {
            throw ResultException("该时间段内没有该考试", 500)
        }
        val pagesAndTitleList = pagesAndTitleRepository.findByPagesId(pageId)
        val list = pagesAndTitleList.map {
            logger.info("page is ${it.pagesId}")
            titleRepository.findById(it.titleId).get()
        }
        return list
    }

    /**
     *
     * 通过 监听rabbitMQ， 进行判题
     * @param `json` 前端字符串
     */
    @Transactional
    @RabbitListener(queues = ["check"])
    @RabbitHandler
    fun addPages(result: Json) {
        logger.info("result json :: $result")
        val ansListInDb =
            studentAnswerRepository.findByStudentIdAndPagesId(result.result.studentId, result.result.pageId)
        if (ansListInDb.isNotEmpty()) {
            return
        }

        val ansList = ArrayList<StudentAnswer>()

        for (it in result.result.answer) {

            val titleInDB = titleRepository.findById(it.id).get() ?: continue //?:throw ResultException("该试题不存在", 500)
            val studentAnswer = StudentAnswer()
            studentAnswer.pagesId = result.result.pageId
            studentAnswer.studentId = result.result.studentId
            studentAnswer.time = Timestamp(System.currentTimeMillis())
            studentAnswer.score = 0.0
            studentAnswer.answer = it.ans
            studentAnswer.titleId = it.id
            val order = titleInDB.orderd
            when (titleInDB.category) {
                // 单选题
                "1" -> {
                    val singleChoiceScore = 5.0
                    logger.info("单选题")
                    if (it.ans == titleInDB.answer) {
                        studentAnswer.score = singleChoiceScore
                    }
                    ansList.add(studentAnswer)
                }
                // 填空题
                "2" -> {
                    logger.info("填空题")
                    // （·-·）
                    val blankTitleScore = 5.0
                    val studentAnswers = it.ans.substringAfter("【").substringBeforeLast("】")
                        .split("】\\s*?【".toRegex())
                    val standardAnswers = titleInDB.answer.substringAfter("【")
                        .substringBeforeLast("】").split("】\\s*?【".toRegex())
                    val blankNumber = standardAnswers.size
                    var blankScore = 0.0

                    // 格式错误
                    if (blankNumber != studentAnswers.size) {
                        studentAnswer.score = 0.0
                    } else {
                        var similarScore = 0.0
                        // 答案有序
                        if (order) {
                            for (i in 0 until standardAnswers.size) {
                                similarScore = WordUtils.blankCheck(studentAnswers[i], standardAnswers[i])
                                blankScore += getScore(similarScore, blankNumber, blankTitleScore)
                            }
                        }
                        // 答案无序
                        else {
                            val standardString = StringBuilder()
                            val studentString = StringBuilder()

                            for (i in 0 until standardAnswers.size) {
                                standardString.append(standardAnswers[i])
                                studentString.append(studentAnswers[i])
                            }
                            similarScore = WordUtils.blankCheck(studentString.toString(), standardString.toString())
                            val x = 1.0 / blankNumber
                            blankScore = (similarScore / x) * (blankTitleScore / blankNumber)
                        }
                        studentAnswer.similarScore = similarScore
                        studentAnswer.score = blankScore
                    }
                    ansList.add(studentAnswer)
                }
                // 简答题
                "3" -> {
                    val ansTitleScore = 10.0
                    logger.info("解答题")
                    val similarScore = WordUtils.ansCheck(it.ans, titleInDB.answer)
                    studentAnswer.similarScore = similarScore
                    studentAnswer.score = (similarScore * 10).toInt().toDouble()
                    println(" score :: ${studentAnswer.score}")
                    ansList.add(studentAnswer)
                }

                else -> {
                    studentAnswer.score = 0.0
                    ansList.add(studentAnswer)
                }
            }
        }

        val titleList = pagesAndTitleRepository.findByPagesId(result.result.pageId)
        val titleId = ArrayList<Long>()
        titleList.map {
            titleId.add(it.titleId)
        }

        val studentTitleId = ArrayList<Long>()
        ansList.map {
            studentTitleId.add(it.titleId)
        }
        titleId.removeAll(studentTitleId)

        for (i in 0 until titleId.size) {
            val ans = StudentAnswer()
            ans.titleId = titleId[i]
            ans.answer = ""
            ans.score = 0.0
            ans.pagesId = result.result.pageId
            ans.studentId = result.result.studentId
            ans.time = Timestamp(System.currentTimeMillis())
            ansList.add(ans)
        }


        studentAnswerRepository.saveAll(ansList)

        // 计算总分
        val scoreList =
            studentAnswerRepository.findByStudentIdAndPagesId(result.result.studentId, result.result.pageId)

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
     * @param studentId 学生id
     * @param pageId 试卷id
     */
    override fun verifyPage(studentId: Long, pageId: Long) {

        val ansList = studentAnswerRepository.findByStudentIdAndPagesId(
            studentId,
            pageId
        )
        if (ansList.isNotEmpty()) {
            throw ResultException("你已经提交过答案，请勿重复提交", 500)
        }
    }


    /**
     * 通过id 获取该学生的所有成绩
     * @param studentId 学生id
     * @return list 返回该学生所有的分数
     */
    override fun listScore(studentId: Long): List<StudentScore> {
        return studentScoreRepository.findByStudentId(studentId)
                ?: throw ResultException("你还没有参加考试", 500)
    }

    /**
     * 查看单张试卷考试成绩
     *
     * 戊戌年大年初二
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
        // 学生提交答案
        val studentAnswer = studentAnswerRepository.findByStudentIdAndPagesId(
            studentId,
            pageId
        )

        if (studentAnswer.isEmpty()) {
            throw ResultException("该学生没有参加该考试", 500)
        }

        println("studentAnswer is ${studentAnswer.size}")
        // 标准答案
        for (i in 0 until studentAnswer.size) {

            val t = titleRepository.findById(studentAnswer[i].titleId).get()
            when (t.category) {
                "1" -> {
                    val selectAns = StudentAnswerSelect()
                    selectAns.id = studentAnswer[i].titleId
                    selectAns.answer = studentAnswer[i].answer
                    selectAns.score = studentAnswer[i].score
                    selectAns.title = t.title
                    selectAns.sectionA = t.sectiona.toString()
                    selectAns.sectionB = t.sectionb.toString()
                    selectAns.sectionC = t.sectionc.toString()
                    selectAns.sectionD = t.sectiond.toString()
                    selectAns.standardAnswer = t.answer
                    ansVO.select.add(selectAns)
                }

                "2" -> {
                    val blankAnswer = com.nuc.libra.vo.StudentAnswer()
                    blankAnswer.id = studentAnswer[i].titleId
                    blankAnswer.answer = studentAnswer[i].answer
                    blankAnswer.score = studentAnswer[i].score
                    blankAnswer.title = t.title
                    blankAnswer.standardAnswer = t.answer
                    ansVO.blank.add(blankAnswer)
                }

                "3" -> {
                    val ans = com.nuc.libra.vo.StudentAnswer()
                    ans.id = studentAnswer[i].titleId
                    ans.answer = studentAnswer[i].answer
                    ans.score = studentAnswer[i].score
                    ans.title = t.title
                    ans.standardAnswer = t.answer
                    ansVO.ans.add(ans)
                }

                "4" -> {

                }
            }

        }
        ansVO.pageId = pageId
        ansVO.score = studentScore.score
        return ansVO
    }


    /**
     * 评分模块
     */
    private fun getScore(similarScore: Double, blankNumber: Int, score: Double): Double {
        return when (similarScore) {
            in 0.0..0.9 -> {
                0.0
            }
            in 0.9..1.0 -> {
                score / blankNumber
            }
            else -> {
                0.0
            }
        }
    }

}