package com.nuc.libra.service.impl

import com.nuc.libra.exception.ResultException
import com.nuc.libra.po.*
import com.nuc.libra.po.StudentAnswer
import com.nuc.libra.repository.*
import com.nuc.libra.service.PaperService
import com.nuc.libra.util.NLPUtils
import com.nuc.libra.util.PathUtils
import com.nuc.libra.vo.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.io.File
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

    @Autowired
    private lateinit var knowledgeRepository: KnowledgeRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var restTemplate: RestTemplate


    /**
     * 获取该班级的所有考试
     * @param classId 班级id
     * @return list 班级考试试卷和考试名称
     */
    override fun listClassPage(classId: Long): List<PageAndClassInfo> {
        val classAndPages = classAndPagesRepository.findByClassId(classId)
        if (classAndPages.isNullOrEmpty()) {
            throw ResultException("没有该班级/该班级没有考试", 500)
        }
        return classAndPages.map {
            val page = pagesRepository.findById(it.pagesId).get()
            val pageAndClassInfo = PageAndClassInfo()
            BeanUtils.copyProperties(it, pageAndClassInfo)
            pageAndClassInfo.pageTitle = page.name
            pageAndClassInfo.pageId = it.pagesId
            pageAndClassInfo.course = courseRepository.getOne(page.courseId).name
            return@map pageAndClassInfo
        }
    }

    @Transactional
    override fun getOnePage(classAndPagesId: Long): PageVO {
        // 获取当前的考试
        val classAndPages = classAndPagesRepository.findById(classAndPagesId).get()
        val nowTime = Timestamp(System.currentTimeMillis())
        if (nowTime.after(classAndPages.endTime) || nowTime.before(classAndPages.startTime)) {
            throw ResultException("该时间段内没有该考试", 500)
        }
        // 获取当前试卷的考试试题
        val pagesAndTitleList = pagesAndTitleRepository.findByPagesId(classAndPages.pagesId)
        val titleVOList = pagesAndTitleList.map {
            val title = titleRepository.findById(it.titleId).get()
            val titleVO = TitleVO()
            BeanUtils.copyProperties(title, titleVO)
            var blankNumber = 0
            if (title.category == "2") {
                val sb = StringBuilder()
                val titles = title.title.split("_{0,50}_".toRegex())
                for (i in 0 until titles.size - 1) {
                    sb.append(titles[i])
                    sb.append("_____")
                }
                sb.append(titles.last())
                title.title = sb.toString().trim()
                blankNumber = titles.size - 1
            }
            titleVO.blankNum = blankNumber
            titleVO
        }
        val pageVO = PageVO()
        /*
         * 题的类型：1单选2填空3简答4程序5算法试题
         */
        titleVOList.forEach {
            when (it.category) {
                "1" -> pageVO.signChoice.add(it)
                "2" -> pageVO.blank.add(it)
                "3" -> pageVO.ansQuestion.add(it)
                "4" -> pageVO.codeQuestion.add(it)
                "5" -> pageVO.algorithm.add(it)
                else -> {
                }
            }
        }
        val paper = getOnePaper(classAndPages.pagesId)
        val studentPageInfo = StudentPageInfo()
        BeanUtils.copyProperties(paper, studentPageInfo)
        studentPageInfo.needTime = classAndPages.needTime
        studentPageInfo.selectScore = paper.selectScore
        pageVO.studentPageInfo = studentPageInfo
        return pageVO
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

        var ansListInDb =
            studentAnswerRepository.findByStudentIdAndPagesId(result.studentId, result.pageId)
        // 该学生是否已经提交过答案
        if (ansListInDb.isNotEmpty()) {
            logger.info("已经提交答案")
            return
        }
        logger.info("准备开始评测")
        val page = pagesRepository.getOne(result.pageId)
        val ansList = ArrayList<StudentAnswer>()
        for (studentAns in result.answer) {
            val standardAnswer = titleRepository.getOne(studentAns.id)
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
                    studentAnswer.score =
                        checkChoice(studentAns.ans, standardAnswer.answer, page.choiceScore.toDouble())
                    ansList.add(studentAnswer)
                }
                //  填空题
                "2" -> {
                    logger.info("blank")
                    studentAnswer.score =
                        checkBlank(studentAns.ans, standardAnswer.answer, page.blankScore.toDouble(), isOrder)
                    ansList.add(studentAnswer)
                }
                // 简答题
                "3" -> {
                    logger.info("answer and question")
                    studentAnswer.score =
                        checkQuestion(studentAns.ans, standardAnswer.answer, page.answerScore.toDouble())
                    ansList.add(studentAnswer)
                }
                "4" -> {

                }
                // 算法试题
                "5" -> {
                    logger.info("alg")
                    studentAnswer.score = checkAlgorithm(
                        studentAnswer.titleId,
                        studentAnswer.pagesId,
                        studentAnswer.studentId,
                        studentAns.ans,
                        // 测试数据集
                        standardAnswer.sectionC!!,
                        page.algorithmScore.toDouble(),
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

        // 下面逻辑 将学生没有作答的试题也存入数据库
        // 查询试题和试卷
        val titleList = pagesAndTitleRepository.findByPagesId(result.pageId)
        // 试题 id 集合
        val titleId = ArrayList<Long>()
        // 将试题 id 存入集合
        titleList.forEach {
            titleId.add(it.titleId)
        }

        val studentTitleId = ArrayList<Long>()
        ansList.forEach {
            studentTitleId.add(it.titleId)
        }
        // 集合做差 计算出学生并没有提交的试题答案
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

        // 保存之前要检查是否有成绩
        ansListInDb =
            studentAnswerRepository.findByStudentIdAndPagesId(result.studentId, result.pageId)
        // 该学生是否已经提交过答案
        if (ansListInDb.isNotEmpty()) {
            return
        }
        studentAnswerRepository.saveAll(ansList)

        var sumScore = 0.0
        ansList.forEach {
            sumScore += it.score
        }

        val studentScore = StudentScore()
        studentScore.pagesId = result.pageId
        studentScore.studentId = result.studentId
        studentScore.status = "1"
        studentScore.score = sumScore
        studentScore.time = Timestamp(System.currentTimeMillis())
        studentScore.dotime = result.doTime
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
        val rootPath = PathUtils.rootPath() + "/page/code/page_$pageId/student_$studentId/"

        inputPath = "$rootPath/${codeId}_${java.util.Date().time}.cpp"
        outputPath = "$rootPath/out/${codeId}_${java.util.Date().time}.cpp"


        val rootFile = File(rootPath)
        if (!rootFile.exists()) {
            rootFile.mkdirs()
        }
        val outFile = File(outputPath)
        if (!outFile.exists()) {
            outFile.mkdirs()
        }
        val inputFile = File(inputPath)

        var resultScore = 0.0
        inputFile.writeText(studentAnswer, charset = Charsets.UTF_8)
        val paramMap = HashMap<String, Any>()
        paramMap["filepath"] = inputPath
        paramMap["outpath"] = outputPath
        paramMap["filename"] = fileName
        paramMap["testSet"] = standardAnswer
        paramMap["limitetime"] = limitTime

        logger.info("post code to gooooooog")
//        code:8
        val message = restTemplate.postForObject("http://106.12.195.114:9000/code", paramMap, String::class.java)!!
        logger.info("message is $message")
        logger.info("code is ${message.substring(6, 7)}")
        when (message.substring(6, 7)) {
            "9" -> {
                resultScore = score
            }
            "8" -> {
                val str = message.substringAfter(":8 ").substringBefore("\"")
                println("str is $str")
                resultScore = str.toFloat() * score
                println("result score $resultScore")

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
            else -> {
                logger.info("状态码为识别")
            }

        }
        return resultScore
    }
    // 教师组卷算法
    // 手动组卷和自动组卷算法
    //

    /**
     * 获取相应试卷试题
     * @param courseId Long
     * @param typeIds IntArray
     * @return Map<String, List<Title>>
     */
    override fun getTitles(courseId: Long, typeIds: IntArray, chapterIds: IntArray): List<List<Title>> {

        val knowledgeList = ArrayList<Knowledge>()

        chapterIds.forEach { id ->
            val knowledges = knowledgeRepository.findByChapterId(id.toLong())
            knowledgeList.addAll(knowledges)
        }
        // 通过知识点和课程查找试题
        val titleKnow = knowledgeList.map { knowledge ->
            titleRepository.findByKnowledgeIdAndCourseId(knowledge.id, courseId)
        }

        // 通过试题类型和课程查找试题
        val titleCategory = typeIds.map {
            titleRepository.findByCategoryAndCourseId(it.toString(), courseId)
        }
        // 将上面的两个集合做交集 -> 通过知识点，试题类型，课程查找出来的试题
        titleCategory.toMutableList().retainAll(titleKnow)

        return titleCategory
    }


    /**
     * 人工组卷方式
     * @param artificialPaperParam ArtificialPaperParam 人工组卷参数
     * @return PageInfo 组卷信息
     */
    @Transactional
    override fun artificial(artificialPaperParam: ArtificialPaperParam): PageInfo {
        val page = Page()
        BeanUtils.copyProperties(artificialPaperParam, page)
        page.status = "1"
        page.totalScores = artificialPaperParam.totalScore
        page.createId = artificialPaperParam.teacherId
        page.createTime = Timestamp(System.currentTimeMillis())
        logger.info(page.toString())
        val newPage = pagesRepository.saveAndFlush(page)
        val pageTitlesList = artificialPaperParam.titleIds.map { titleId ->
            PagesAndTitle().apply {
                this.pagesId = newPage.id
                this.titleId = titleId
            }
        }
        val allTitles = pagesAndTitleRepository.saveAll(pageTitlesList)
        if (allTitles.isNullOrEmpty()) {
            throw ResultException("组卷失败", 500)
        }

        val titleList = allTitles.map { pageAndTitle ->
            titleRepository.findById(pageAndTitle.titleId).get()
        }
        val diff = calDifficulty(titleList, page.id)

//        knowledgeRepository.findById()

        val knowledgeList = titleList.map {
            knowledgeRepository.getOne(it.knowledgeId).name
        }

        return PageInfo().apply {
            this.titles = titleList
            this.difficulty = diff
            this.courseName = courseRepository.findById(page.courseId).get().name
            this.teacherName = teacherRepository.findById(page.createId).get().name
            this.paperTitle = page.name
            this.totalScores = page.totalScores
            this.selectScore = page.choiceScore
            this.blankScore = page.blankScore
            this.answerScore = page.answerScore
            this.codeScore = page.codeScore
            this.algorithmScore = page.algorithmScore
            this.knowledgeList = knowledgeList
            this.createTime = page.createTime.toString()
        }
    }

    /**
     *
     * @param courseId Long
     * @param typeIds IntArray
     */
    override fun ai(courseId: Long, typeIds: IntArray) {

    }

    /**
     * 计算试题难度
     * @param titlesList List<Title> 试题集合
     * @param pageId Long 试卷id
     * @return Float 难度
     */
    private fun calDifficulty(titlesList: List<Title>, pageId: Long): Float {
        val page = pagesRepository.findById(pageId).get()
        var diff = 0f
        titlesList.forEach { title ->
            when (title.category) {
                "1" -> {
                    diff += (title.difficulty * page.choiceScore).toFloat()
                }
                "2" -> {
                    diff += (title.difficulty * page.blankScore).toFloat()

                }
                "3" -> {
                    diff += (title.difficulty * page.answerScore).toFloat()

                }
                "4" -> {
                    diff += (title.difficulty * page.codeScore).toFloat()

                }
                "5" -> {
                    diff += (title.difficulty * page.algorithmScore).toFloat()
                }
            }
        }
        return diff
    }

    /**
     * 获取所有的试卷
     * @return List<PageInfo>
     */
    override fun getAllPapers(): List<PageInfo> {
        val paperList = pagesRepository.findAll()

        return paperList.map { page ->
            val pageInfo = PageInfo()
            BeanUtils.copyProperties(page, pageInfo)
            pageInfo.courseName = courseRepository.findById(page.courseId).get().name
            pageInfo.createTime = page.createTime.toString()
            pageInfo.pageId = page.id
            pageInfo.paperTitle = page.name
            return@map pageInfo
        }

    }


    /**
     * 通过试卷计算难度
     * @param page Page
     * @return Float
     */
    private fun calDifficulty(page: Page): Float {
        val pageAndTitle = pagesAndTitleRepository.findByPagesId(page.id)
        var diff = 0.0
        pageAndTitle.map {
            titleRepository.findById(it.titleId).get()
        }.forEach {
            when (it.category) {
                "1" -> {
                    diff += it.difficulty * page.choiceScore
                }
                "2" -> {
                    diff += it.difficulty * page.blankScore
                }
                "3" -> {
                    diff += it.difficulty * page.answerScore
                }
                "4" -> {
                    diff += it.difficulty * page.codeScore
                }
                "5" -> {
                    diff += it.difficulty * page.algorithmScore
                }
            }
        }
        return diff.toFloat()
    }


    /**
     * 获取单张试卷信息
     * @param paperId Long
     * @return PageInfo
     */
    override fun getOnePaper(paperId: Long): PageInfo {
        val page = pagesRepository.findById(paperId).get()
        val pageInfo = PageInfo()
        BeanUtils.copyProperties(page, pageInfo)
        pageInfo.selectScore = page.choiceScore
        pageInfo.paperTitle = page.name
        pageInfo.difficulty = calDifficulty(page)
        pageInfo.teacherName = teacherRepository.findById(page.createId).get().name
        pageInfo.courseName = courseRepository.findById(page.courseId).get().name
        val titleList = pagesAndTitleRepository.findByPagesId(page.id).map {
            titleRepository.findById(it.titleId).get()
        }
        pageInfo.createTime = page.createTime.toString()
        pageInfo.titles = titleList

        return pageInfo
    }


    @Transactional
    override fun savePageAndClass(pageClassParam: PageClassParam) {
        val pageClassList = pageClassParam.classIds.map {
            val classAndPages = ClassAndPages()
            classAndPages.addTime = Timestamp(System.currentTimeMillis())
            classAndPages.startTime = Timestamp(pageClassParam.startTime)
            classAndPages.endTime = Timestamp(pageClassParam.endTime)
            classAndPages.classId = it
            classAndPages.pagesId = pageClassParam.pageId
            classAndPages.employeeId = pageClassParam.teacherId
            return@map classAndPages
        }

        classAndPagesRepository.saveAll(pageClassList)
    }

    override fun getCreateName(pageId: Long): String {
        val page = pagesRepository.findById(pageId).get()
        val teacher = teacherRepository.findById(page.createId).get()
        return teacher.name

    }
}
