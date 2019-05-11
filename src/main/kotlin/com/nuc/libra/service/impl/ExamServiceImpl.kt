package com.nuc.libra.service.impl

import com.nuc.libra.exception.ResultException
import com.nuc.libra.po.StudentAnswer
import com.nuc.libra.po.StudentScore
import com.nuc.libra.repository.*
import com.nuc.libra.service.ExamService
import com.nuc.libra.util.NLPUtils
import com.nuc.libra.vo.ClassScoreInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
    private lateinit var classRepository: ClassRepository

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
     */
    override fun getStudentErrorInfo(classId: Long, pageId: Long) {
        val studentList = studentRepository.findStudentsByClassId(classId)
        val blankAnswerList = ArrayList<BlankAnswerInfo>()
        val answerAnserList = ArrayList<BlankAnswerInfo>()
        val choiceAnswerList = ArrayList<ChoiceAnswerInfo>()
        val page = pagesRepository.findById(pageId).get()
        val pageAndTitleList = pagesAndTitleRepository.findByPagesId(page.id)
        pageAndTitleList.parallelStream().forEach { pageAndTitle ->
            val title = titleRepository.findById(pageAndTitle.titleId).get()
            var blankString: String = ""
            var answerString = ""
            studentList.parallelStream().forEach { student ->
                val studentAns =
                    studentAnswerRepository.findByStudentIdAndTitleIdAndPagesId(student.id, title.id, pageId)
                            ?: return@forEach

                when (title.category) {
                    // 选择题
                    "1" -> {
                        if (page.choiceScore * 0.6 > studentAns.score) {
                            // title id
                            // 选项
                            val choice = ChoiceAnswerInfo().apply {
                                this.choiceAns = studentAns.answer
                                this.choiceId = title.id
                            }
                            choiceAnswerList.add(choice)
                        }

                    }
                    // 填空题
                    "2" -> {
                        if (page.blankScore * 0.6 > studentAns.score) {
                            blankString += studentAns.answer + " "
                        }

                    }
                    "3" -> {
                        if (page.answerScore * 0.6 > studentAns.score) {
                            answerString += studentAns.answer + " "
                        }
                    }

                }
            }
            if (title.category == "2") {
                val blankInfo = BlankAnswerInfo().apply {
                    this.blankId = title.id
                    this.blankString = blankString
                }
                blankAnswerList.add(blankInfo)
            }
            if (title.category == "3") {
                val blankInfo = BlankAnswerInfo().apply {
                    this.blankId = title.id
                    this.blankString = answerString
                }
                answerAnserList.add(blankInfo)
            }
        }

        // 简答题词频
        val answerWordFrequency = answerAnserList.parallelStream().map {
            NLPUtils.wordFrequency(it.blankString)
        }

        // 填空题词频
        val blankWordFrequency = blankAnswerList.parallelStream().map {
            NLPUtils.wordFrequency(it.blankString)
        }

//        println("blankAnswerList = ${blankAnswerList}")
//        val ansWordFrequency = NLPUtils.wordFrequency(answerAnserList[0].blankString)
//        val blankWordFrequency = NLPUtils.wordFrequency()

//        println("wordFrequency = ${wordFrequency}")

    }

    class ChoiceAnswerInfo {
        lateinit var choiceAns: String
        var choiceId: Long = 0L
        override fun toString(): String {
            return "ChoiceAnswerInfo(choiceAns='$choiceAns', choiceId=$choiceId)"
        }

    }

    class BlankAnswerInfo {
        lateinit var blankString: String
        var blankId = 0L
        override fun toString(): String {
            return "BlankAnswerInfo(blankString='$blankString', blankId=$blankId)"
        }


    }
}