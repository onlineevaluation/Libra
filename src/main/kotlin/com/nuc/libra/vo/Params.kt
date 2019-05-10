package com.nuc.libra.vo

import io.swagger.annotations.ApiModel
import java.sql.Date
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author 杨晓辉 2018-12-29 16:06
 * 用于接收前端参数的类
 * 所有的类采用data class
 * 所有的后缀采用 **param**
 */

/**
 * 用于接收页面的 `UserParam` 值
 * @param username 用户名
 * @param password 密码
 */
@ApiModel("登录接收数据")
data class UserParam(
    val username: String,
    val password: String
)

/**
 * 考试试卷信息
 * @property classId Long 班级id
 * @property pageId Long 试卷id
 * @constructor
 */
@ApiModel("考试试卷信息")
data class ExamParam(

    val classId: Long,
    val pageId: Long
)

/**
 * 返回的成绩视图
 */
class StudentScoreParam {
    var id: Long = 0L
    var studentId: Long = 0L
    var pagesId: Long = 0L
    var score: Double = 0.0
    var time: Timestamp? = null
    var dotime: Date? = null
    lateinit var classRank: String
    lateinit var gradeRank: String
    lateinit var pageTitle: String


    override fun toString(): String {
        return "StudentScoreParam(id=$id, studentId=$studentId, score=$score, time=$time, dotime=$dotime, classRank=$classRank, gradeRank=$gradeRank)"
    }


}

/**
 * 试卷详情
 */
class PageDetailsParam {
    var id: Long = 0L

    var pageId: Long = 0L

    /**
     * 分数
     */
    var score: Double = 0.0
    /**
     * 试卷名称
     */
    lateinit var pageTitle: String
    /**
     * 学科
     */
    lateinit var course: String
    /**
     * 完成用时
     */
    var doTime: String? = null

    var select: ArrayList<StudentAnswerSelect> = ArrayList()
    var blank: ArrayList<StudentAnswer> = ArrayList()
    var ans = ArrayList<StudentAnswer>()
    var algorithm = ArrayList<StudentAnswer>()
}

/**
 * 学生答案
 * @property id Long 答案id
 * @property answer String 答案
 * @property score Double 分数
 * @property standardAnswer String 标准答案
 * @property title String 标题
 */
open class StudentAnswer {
    var id: Long = 0L
    var answer: String = ""
    var score: Double = 0.0
    var standardAnswer = ""
    lateinit var title: String
}

/**
 * 选项
 * @property sectionA String
 * @property sectionB String
 * @property sectionC String
 * @property sectionD String
 */
class StudentAnswerSelect : StudentAnswer() {
    var sectionA: String = ""
    var sectionB: String = ""
    var sectionC: String = ""
    var sectionD: String = ""
}


/**
 * 用户的代码提交
 */
data class Code(val id: Long, val codeString: String, val language: String)


/**
 * 学生提交有异议的试题
 * @param titleId 试题编号
 * @param studentId 学生id
 * @param content 提交内容
 */
data class WrongTitleParam(val titleId: Long, val studentId: Long, val content: String)

/**
 * 试卷验证id
 * @param studentId 学生id
 * @param pageId 试卷id
 */
data class VerifyPageParam(val studentId: Long, val pageId: Long)

/**
 * 获取前 10 名 参数
 * @property teacherId Long
 * @property pageId Long
 * @property classId Long
 * @constructor
 */
data class ClassAndPageParam(val teacherId: Long, val pageId: Long, val classId: Long)

/**
 * 试卷信息
 * @property courseId Long 课程编号
 * @property titleType IntArray 试题类型
 * @property chapterIds 章节id
 * @constructor
 */
data class PaperTitleTypeParam(val courseId: Long, val titleType: IntArray, val chapterIds: IntArray) {
    override fun toString(): String {
        return "PaperTitleTypeParam(courseId=$courseId, titleType=${Arrays.toString(titleType)}, chapterIds=${Arrays.toString(
            chapterIds
        )})"
    }
}

/**
 * 手工组卷时收到的参数
 * @property courseId Long 课程id
 * @property teacherId Long 教师id
 * @property paperTitle String 试卷标题
 * @property titleIds LongArray 试题编号
 * @property choiceScore Double 选择题单项分值
 * @property blankScore Double 填空题单项分值
 * @property answerScore Double 简答题单项分值
 * @property codeScore Double 代码题单项分值
 * @property algorithmScore Double 算法题单项分值
 * @constructor
 */
data class ArtificialPaperParam(
    val courseId: Long,
    val teacherId: Long,
    val paperTitle: String,
    val titleIds: LongArray,
    val choiceScore: Float,
    val blankScore: Float,
    val answerScore: Float,
    val codeScore: Float,
    val algorithmScore: Float,
    val totalScore: Float
)

/**
 * 试卷和班级关系
 * @property classIds LongArray
 * @property teacherId Long
 * @property startTime Long
 * @property endTime Long
 * @property pageId Long
 * @constructor
 */
data class PageClassParam(
    val classIds: LongArray,
    val teacherId: Long,
    val startTime: Long,
    val endTime: Long,
    val pageId: Long
)