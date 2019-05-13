package com.nuc.libra.service.impl

import com.nuc.libra.repository.*
import com.nuc.libra.service.ExamService
import com.nuc.libra.util.NLPUtils
import com.nuc.libra.vo.AnswerInfo
import com.nuc.libra.vo.ClassScoreInfo
import com.nuc.libra.vo.ErrorInfo
import com.nuc.libra.vo.TitleErrorInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.streams.toList

/**
 * @author 杨晓辉 2019/5/9 9:26
 */
@Service
class ExamServiceImpl : ExamService {

    @Autowired
    private lateinit var studentRepository: StudentRepository
    @Autowired
    private lateinit var classAndPagesRepository: ClassAndPagesRepository

    @Autowired
    private lateinit var studentScoreRepository: StudentScoreRepository

    @Autowired
    private lateinit var studentAnswerRepository: StudentAnswerRepository

    @Autowired
    private lateinit var titleRepository: TitleRepository

    @Autowired
    private lateinit var pagesRepository: PagesRepository

    @Autowired
    private lateinit var pagesAndTitleRepository: PageAndTitleRepository


    /**
     * 获取学生考试总数
     * @param studentId Long
     * @return Long
     */
    override fun getClassExamPageCount(studentId: Long): Int {
        val student = studentRepository.getOne(studentId)
        return classAndPagesRepository.findByClassId(student.classId).count()
    }

    /**
     * 获取班级考试基本信息
     * @param classId Long
     * @param pageId Long
     * @return ClassScoreInfo
     */
    override fun getStudentAllErrorTitleKnowledge(classId: Long, pageId: Long): ClassScoreInfo {
        val studentList = studentRepository.findStudentsByClassId(classId)
        val scoreList = studentList.mapNotNull { student ->
            val studentScore =
                studentScoreRepository.findStudentScoresByStudentIdAndPagesId(student.id, pageId)
            studentScore
        }.sortedByDescending { it.score }
        val average = scoreList.sumByDouble { it.score } / scoreList.size
        // 最高分
        val highestScore = scoreList.first().score.toFloat()
        // 最低分
        val lowestScore = scoreList.last().score.toFloat()

        val classScoreInfo = ClassScoreInfo().apply {
            this.average = average.toFloat()
            this.highestScore = highestScore
            this.lowestScore = lowestScore


        }
        return classScoreInfo
    }


    /**
     * 获取错误知识点总体信息
     * @param classId Long
     * @param pageId Long
     * todo(该方法有点复杂，有时间要进行抽象简化，把一些方法抽取出来)
     */
    override fun getStudentErrorInfo(classId: Long, pageId: Long): TitleErrorInfo {
        val studentList = studentRepository.findStudentsByClassId(classId)
        val blankAnswerList = ArrayList<AnswerInfo>()
        val answerAnswerList = ArrayList<AnswerInfo>()
        val choiceAnswerList = ArrayList<AnswerInfo>()
        val codeAnswerList = ArrayList<AnswerInfo>()
        val algorithmAnswerList = ArrayList<AnswerInfo>()
        val page = pagesRepository.findById(pageId).get()
        val pageAndTitleList = pagesAndTitleRepository.findByPagesId(page.id)
        pageAndTitleList.parallelStream().forEach { pageAndTitle ->
            val title = titleRepository.findById(pageAndTitle.titleId).get()
            var blankString: String = ""
            var answerString = ""
            var ansCount = 0
            var errorCount = 0
            studentList.parallelStream().forEach { student ->
                val studentAns =
                    studentAnswerRepository.findByStudentIdAndTitleIdAndPagesId(student.id, title.id, pageId)
                            ?: return@forEach
                ansCount++
                when (title.category) {
                    // 选择题
                    "1" -> {
                        val choice = AnswerInfo().apply {
                            this.ans = studentAns.answer
                            this.id = title.id
                        }
                        choiceAnswerList.add(choice)
                    }
                    // 填空题
                    "2" -> {
                        if (page.blankScore * 0.6 > studentAns.score) {
                            blankString += studentAns.answer + " "
                            errorCount++
                        }

                    }
                    // 简单题
                    "3" -> {
                        if (page.answerScore * 0.6 > studentAns.score) {
                            answerString += studentAns.answer + " "
                            errorCount++
                        }
                    }
                    // 算法题
                    "4" -> {
                        if (page.codeScore * 0.6 > studentAns.score) {
                            errorCount++
                        }
                    }
                    "5" -> {
                        if (page.algorithmScore * 0.6 > studentAns.score) {
                            errorCount++
                        }
                    }
                }
            }

            if (title.category == "2") {
                val blankInfo = AnswerInfo().apply {
                    this.id = title.id
                    this.ans = blankString
                    this.errorRate = errorCount * 0.1f / ansCount
                }
                blankAnswerList.add(blankInfo)
            }

            if (title.category == "3") {
                val blankInfo = AnswerInfo().apply {
                    this.id = title.id
                    this.ans = answerString
                    this.errorRate = errorCount * 0.1f / ansCount
                }
                answerAnswerList.add(blankInfo)
            }
            if (title.category == "4") {
                val codeAnswer = AnswerInfo().apply {
                    this.errorRate = errorCount * 0.1f / ansCount
                    this.ans = ""
                }
                codeAnswerList.add(codeAnswer)
            }
            if (title.category == "5") {
                val algorithmAnswer = AnswerInfo().apply {
                    this.errorRate = errorCount * 0.1f / ansCount
                    this.ans = ""
                }
                algorithmAnswerList.add(algorithmAnswer)
            }
        }


        val choiceGroupList = choiceAnswerList.groupBy {
            it.id
        }.map {
            AnswerInfo().apply {
                this.id = it.key
                this.ans = it.value.map { answerInfo ->
                    answerInfo.ans
                }.toString()
            }
        }

        val choiceErrorList = choiceGroupList.map {
            val wordFrequency = NLPUtils.wordFrequency(it.ans, 1)
            return@map ErrorInfo().apply {
                this.frequency = wordFrequency
                this.titleId = it.id
                val title = titleRepository.findById(it.id).get()
                this.title =
                    "${title.title}\n  A:${title.sectionA} \n B:${title.sectionB} \n" +
                            " C:${title.sectionC} \n D:${title.sectionD}"
            }
        }
        // 简答题词频
        val answerWordFrequency = answerAnswerList.parallelStream().map {
            val wordFrequency = NLPUtils.wordFrequency(it.ans)
            return@map ErrorInfo().apply {
                this.frequency = wordFrequency
                this.titleId = it.id
                this.title = titleRepository.findById(it.id).get().title
            }
        }.toList()
        // 填空题词频
        val blankWordFrequency = blankAnswerList.parallelStream().map {
            val wordFrequency = NLPUtils.wordFrequency(it.ans)
            return@map ErrorInfo().apply {
                this.frequency = wordFrequency
                this.titleId = it.id
                this.title = titleRepository.findById(it.id).get().title
            }
        }.toList()
        // 算法题

        // 完成所有的映射


        // 算法题映射

        // 设置
        val titleErrorInfo = TitleErrorInfo()
        titleErrorInfo.choiceErrorList = choiceErrorList
        titleErrorInfo.blankErrorWordFrequency = blankWordFrequency
        titleErrorInfo.answerErrorWordFrequency = answerWordFrequency

        return titleErrorInfo

    }

}