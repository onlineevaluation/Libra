package com.nuc.libra.service.impl

import com.nuc.libra.entity.result.Result
import com.nuc.libra.exception.ResultException
import com.nuc.libra.jni.Capricornus
import com.nuc.libra.jni.GoString
import com.nuc.libra.po.ClassAndPages
import com.nuc.libra.po.StudentAnswer
import com.nuc.libra.po.StudentScore
import com.nuc.libra.po.Title
import com.nuc.libra.repository.*
import com.nuc.libra.service.PaperService
import com.nuc.libra.util.NLPUtils
import com.nuc.libra.vo.PageDetailsParam
import com.nuc.libra.vo.StudentAnswerSelect
import com.nuc.libra.vo.StudentScoreParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.sql.Date
import java.sql.Timestamp
import javax.transaction.Transactional

/**
 * @author 杨晓辉 2018/2/3 16:04
 * @Version 1.0
 * 代码千万行，注释第一行
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

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    /**
     * 获取该班级的所有考试
     * @param classId 班级id
     * @return list 班级考试试卷和考试名称
     */
    override fun listClassPage(classId: Long): List<ClassAndPages> {
        val classAndPages = classAndPagesRepository.findByClassId(classId)
        if (classAndPages.isNullOrEmpty()) {
            throw ResultException("没有该班级/该班级没有考试", 500)
        }
        return classAndPages
    }

    /**
     * 获取指定考试的试题
     * @param pageId 试卷id
     * @param classId 班级id
     * @return 返回试卷试题
     */
    @Transactional
    override fun getOnePage(classId: Long, pageId: Long): List<Title> {
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
            titleRepository.findById(it.titleId).get()
        }

    }

    /**
     *
     * 通过 监听 rabbitMQ， 进行判题
     * @param `json` 前端字符串
     */
    @Transactional
    @RabbitListener(queues = ["check"])
    @RabbitHandler
    fun addPages(result: Result) {
        val ansListInDb =
            studentAnswerRepository.findByStudentIdAndPagesId(result.studentId, result.pageId)
        // 该学生是否已经提交过答案
        if (ansListInDb.isNotEmpty()) {
            return
        }
        val ansList = ArrayList<StudentAnswer>()
        for (studentAns in result.answer) {
            val standardAnswer = titleRepository.findById(studentAns.id).get()
            val studentAnswer = StudentAnswer()
            studentAnswer.pagesId = result.pageId
            studentAnswer.studentId = result.studentId
            studentAnswer.time = Timestamp(System.currentTimeMillis())
            studentAnswer.score = 0.0
            studentAnswer.answer = studentAns.ans
            studentAnswer.titleId = studentAns.id
            val isOrder = standardAnswer.orderd
            when (standardAnswer.category) {
                // 单选题
                "1" -> {
                    studentAnswer.score = checkChoice(studentAns.ans, standardAnswer.answer, 5.0)
                    ansList.add(studentAnswer)
                }
                // 填空题
                "2" -> {
                    logger.info("填空题")
                    studentAnswer.score = checkBlank(studentAns.ans, standardAnswer.answer, 5.0, isOrder)
                    ansList.add(studentAnswer)
                }
                // 简答题
                "3" -> {
                    val ansTitleScore = 10.0
                    logger.info("解答题")
                    studentAnswer.score = checkQuestion(studentAns.ans, standardAnswer.answer, ansTitleScore)
                    ansList.add(studentAnswer)
                }
                "4" -> {

                }
                // 算法试题
                "5" -> {
                    logger.info("算法试题")
                    logger.info("student ans is ${studentAns.ans}")
                    val algorithmScore = 10.0
                    studentAnswer.score = checkAlgorithm(
                        studentAnswer.titleId,
                        studentAnswer.pagesId,
                        studentAnswer.studentId,
                        studentAns.ans,
                        // 测试数据集
                        standardAnswer.sectionC!!,
                        algorithmScore,
                        standardAnswer.sectionA!!.toInt(),// 限制时间
                        standardAnswer.sectionB!!.toInt() // 限制内存
                    )
                    ansList.add(studentAnswer)
                }
                else -> {
                    studentAnswer.score = 0.0
                    ansList.add(studentAnswer)
                }
            }
        }


        val titleList = pagesAndTitleRepository.findByPagesId(result.pageId)
        val titleId = ArrayList<Long>()
        titleList.forEach {
            titleId.add(it.titleId)
        }
        val studentTitleId = ArrayList<Long>()
        ansList.forEach {
            studentTitleId.add(it.titleId)
        }
        titleId.removeAll(studentTitleId)
        for (i in 0 until titleId.size) {
            val ans = StudentAnswer()
            ans.titleId = titleId[i]
            ans.answer = ""
            ans.score = 0.0
            ans.pagesId = result.pageId
            ans.studentId = result.studentId
            ans.time = Timestamp(System.currentTimeMillis())
            ansList.add(ans)
        }
        studentAnswerRepository.saveAll(ansList)
        // todo 2019年2月4日 为什么要存了再去取？ 不矛盾吗？ 为什么不直接算分数
        // 计算总分
        val scoreList =
            studentAnswerRepository.findByStudentIdAndPagesId(result.studentId, result.pageId)

        var sumScore = 0.0
        scoreList.forEach {
            sumScore += it.score
        }

        val studentScore = StudentScore()
        studentScore.pagesId = result.pageId
        studentScore.studentId = result.studentId
        studentScore.status = "1"
        studentScore.score = sumScore
        studentScore.time = Timestamp(System.currentTimeMillis())
        studentScore.dotime = Date(System.currentTimeMillis())
        logger.info("student score is ${studentScore.score}")
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
    override fun listScore(studentId: Long): List<StudentScoreParam> {
        // 获得该考生所有的考试信息
        val student = studentRepository.findById(studentId).get()
        val studentScoreList = ArrayList<StudentScoreParam>()
        // 查找同班同学
        val classmate = studentRepository.findStudentsByClassId(student.classId)
        // 该学生的所有考试
        val list = studentScoreRepository.findByStudentId(studentId)
                ?: throw ResultException("你还没有参加任何考试", 500)

        for (i in 0 until list.size) {
            // 存放单张试卷的全班分数
            val scoreList = ArrayList<Double>()
            for (j in 0 until classmate.size) {
                val pageScore =
                    studentScoreRepository.findByPagesIdAndStudentId(list[i].pagesId, classmate[j].id) ?: continue

                scoreList.add(pageScore.score)
            }
            // 计算班级排名
            val selfScore = studentScoreRepository.findByPagesIdAndStudentId(list[i].pagesId, studentId)
                    ?: throw ResultException("你还没有参加该考试", 500)
            scoreList.sortDescending()
            var classRank = 1
            for (j in 0 until scoreList.size) {
                if (scoreList[j] == selfScore.score) {
                    classRank = j
                    break
                }
            }
            // 计算年级排名
            val studentScores = studentScoreRepository.findStudentScoresByPagesId(list[i].pagesId)
                    ?: continue
            val gradeScores = ArrayList<Double>()
            studentScores.forEach { it ->
                gradeScores.add(it.score)
            }
            var gradeRank = 1
            gradeScores.sortedDescending()
            for (j in 0 until gradeScores.size) {
                if (gradeScores[j] == selfScore.score) {
                    gradeRank = j
                    break
                }
            }
            val studentScoreParam = StudentScoreParam()
            BeanUtils.copyProperties(list[i], studentScoreParam)
            val page = pagesRepository.findById(list[i].pagesId).get()
            studentScoreParam.pageTitle = page.name
            studentScoreParam.classRank = String.format("%.2f", (classRank.toDouble() / classmate.size) * 100)
            studentScoreParam.gradeRank = String.format("%.2f", (gradeRank.toDouble() / studentScores.size) * 100)
            studentScoreList.add(studentScoreParam)
        }

        return studentScoreList
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
    override fun getPageScore(pageId: Long, studentId: Long): PageDetailsParam {
        val pageDetails = PageDetailsParam()
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
        val page = pagesRepository.findById(pageId).get()
        pageDetails.pageTitle = page.name
        logger.info("page is $page")
        pageDetails.course = courseRepository.findById(page.courseId).get().name

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
                    selectAns.sectionA = t.sectionA.toString()
                    selectAns.sectionB = t.sectionB.toString()
                    selectAns.sectionC = t.sectionC.toString()
                    selectAns.sectionD = t.sectionD.toString()
                    selectAns.standardAnswer = t.answer
                    pageDetails.select.add(selectAns)
                }

                "2" -> {
                    val blankAnswer = com.nuc.libra.vo.StudentAnswer()
                    blankAnswer.id = studentAnswer[i].titleId
                    blankAnswer.answer = studentAnswer[i].answer
                    blankAnswer.score = studentAnswer[i].score
                    blankAnswer.title = t.title
                    blankAnswer.standardAnswer = t.answer
                    pageDetails.blank.add(blankAnswer)
                }

                "3" -> {
                    val ans = com.nuc.libra.vo.StudentAnswer()
                    ans.id = studentAnswer[i].titleId
                    ans.answer = studentAnswer[i].answer
                    ans.score = studentAnswer[i].score
                    ans.title = t.title
                    ans.standardAnswer = t.answer
                    pageDetails.ans.add(ans)
                }

                "4" -> {
                    val code = com.nuc.libra.vo.StudentAnswer()
                    code.id = studentAnswer[i].titleId

                }
                "5" -> {
                    val algorithm = com.nuc.libra.vo.StudentAnswer()
                    algorithm.id = studentAnswer[i].titleId
                    BeanUtils.copyProperties(studentAnswer[i], algorithm)
                    algorithm.title = t.title
                    algorithm.standardAnswer = t.answer
                    pageDetails.algorithm.add(algorithm)
                }
            }

        }
        pageDetails.pageId = pageId
        pageDetails.score = studentScore.score
        return pageDetails
    }

    /**
     * 评分模块
     */
    private fun calculationScore(similarScore: Double, blankNumber: Int, score: Double): Double {
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

    /**
     * 选择题评分模块
     * 和标准答案直接进行比对
     * @param standardAnswer 标准答案
     * @param studentAnswer 学生答案
     * @param score 该题分数
     */
    private fun checkChoice(studentAnswer: String, standardAnswer: String, score: Double): Double {
        val answer = "【$studentAnswer】"
        if (answer.equals(standardAnswer, ignoreCase = true)) {
            return score
        }
        return 0.0
    }

    /**
     * 填空题进行评分
     * 1. 检查提交的答案数是否和空数一致，如果不一致则为 **0** 分
     * 2. 对有序答案进行验证，有序答案没空进行答案校验
     * 3. 对无序答案进行验证，将无序答案拼接为一个完整的字符串进行相似度比较
     * @param studentAnswer 学生答案
     * @param standardAnswer 标准答案
     * @param score 该试题分数
     * @param isOrder 是否有序
     * @return 返回该试题分数
     */
    private fun checkBlank(studentAnswer: String, standardAnswer: String, score: Double, isOrder: Boolean): Double {
        val studentAnswerList = studentAnswer.substringAfter("【").substringBeforeLast("】").split("】\\s*?【".toRegex())
        val standardAnswerList = standardAnswer.substringAfter("【").substringBeforeLast("】").split("】\\s*?【".toRegex())
        // 填空题空的数量
        val blankNumber = standardAnswerList.size
        if (blankNumber != studentAnswerList.size) {
            return 0.0
        }
        var blankScore = 0.0
        var similar: Double
        // 有序答案
        if (isOrder) {
            for (index in 0 until blankNumber) {
                similar = NLPUtils.docSimilar(studentAnswerList[index], standardAnswerList[index])
                blankScore += calculationScore(similar, blankNumber, score)
            }
            return blankScore
        }
        // 无序答案
        else {
            val studentAnswerSb = StringBuilder()
            val standardAnswerSb = StringBuilder()
            for (ans in studentAnswerList) {
                studentAnswerSb.append(ans)
            }
            for (ans in standardAnswerList) {
                standardAnswerSb.append(ans)
            }
            similar = NLPUtils.docSimilar(studentAnswerSb.toString(), standardAnswerSb.toString())
            // x 代表着未知 所以下面的步骤只有天知道！
            // 我猜是计算平均分
            val x = 1.0 / blankNumber
            blankScore = (similar / x) * (score / blankNumber)
            return blankScore
        }
    }

    /**
     * 简答题评分
     * @param studentAnswer 学生答案
     * @param standardAnswer 标准答案
     * @param score 该题满分
     *
     * @return 学生所得分数
     */
    private fun checkQuestion(studentAnswer: String, standardAnswer: String, score: Double): Double {
        val similar = NLPUtils.docSimilar(standardAnswer, studentAnswer)
        val studentScore = (score * similar).toInt().toDouble()
        logger.info("学生的分数是 $studentScore")
        return if (studentScore < 0) {
            0.0
        } else {
            studentScore
        }
    }

    /**
     * 算法题测评
     * @param studentAnswer 学生代码
     * @param standardAnswer 测试数据集
     * @param score 试题得分
     * @param limitTime 限制时间
     * @param limitMemory 限制内存
     */
    private fun checkAlgorithm(
        codeId: Long,
        pageId: Long,
        studentId: Long,
        studentAnswer: String,
        standardAnswer: String,
        score: Double,
        limitTime: Int,
        limitMemory: Int
    ): Double {


        val inputPath: String
        val outputPath: String
        val fileName = "${codeId}_${java.util.Date().time}_out"
        val osName = System.getProperty("os.name")
        if (osName.contains("windows", true)) {
            inputPath = "d:/page_$pageId/student_$studentId/${codeId}_${java.util.Date().time}.cpp"
            outputPath = "d:/page_out_$pageId/student_$studentId"
        } else {
            inputPath = "~/page_$pageId/student_$studentId/${codeId}_${java.util.Date().time}.cpp"
            outputPath = "~/page_out_$pageId/student_$studentId"
        }
        val outFile = File(outputPath)
        if (!outFile.exists()) {
            outFile.mkdirs()
        }
        val codePath = File(inputPath)
        if (!codePath.exists()) {
            val parent = codePath.parent
            val parentFile = File(parent)
            if (!parentFile.exists()) {
                parentFile.mkdirs()
            }
            codePath.createNewFile()
        }

        var resultScore = 0.0
     
        codePath.writeText(studentAnswer, charset = Charsets.UTF_8)
        val result = Capricornus.INSTANCE.judgeCode(
            GoString.ByValue(inputPath),
            GoString.ByValue(outputPath),
            GoString.ByValue(fileName),
            GoString.ByValue(standardAnswer),
            limitTime
        )

        when (result.substring(5, 6)) {
            "9" -> {
                resultScore = score
            }
            "8" -> {

            }
            "7" -> {

            }
            "6" -> {

            }
            "5" -> {

            }
            "4" -> {

            }
            "3" -> {
                logger.error("运行出错")
            }
            "2" -> {

            }
            "1" -> {

            }

        }
        return resultScore
    }
}


