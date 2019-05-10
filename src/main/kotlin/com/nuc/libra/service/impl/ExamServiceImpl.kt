package com.nuc.libra.service.impl

import com.nuc.libra.po.StudentAnswer
import com.nuc.libra.po.StudentScore
import com.nuc.libra.repository.*
import com.nuc.libra.service.ExamService
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

        // 易错点
//        studentList.map { student ->
//            val answerList = studentAnswerRepository.findByStudentIdAndPagesId(student.id, pageId)
//            val knowledgeList = answerList.filter { studentAnswer ->
//                studentAnswer.score == 0.0
//            }.groupBy {
//                titleRepository.findById(it.titleId).get().knowledgeId
//            }
//
//            answerList
//        }

        val classScoreInfo = ClassScoreInfo().apply {
            this.average = average.toFloat()
            this.highestScore = highestScore
            this.lowestScore = lowestScore


        }
        return classScoreInfo
    }


    fun getStudentErrorInfo(classId: Long, pageId: Long) {
        val page = pagesRepository.findById(pageId).get()
        val pageAndTitleList = pagesAndTitleRepository.findByPagesId(page.id)


        // 获取非小于60% 的试卷答案


    }
}