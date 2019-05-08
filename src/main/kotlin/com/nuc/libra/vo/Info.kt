package com.nuc.libra.vo

import com.nuc.libra.po.Course
import com.nuc.libra.po.Knowledge
import com.nuc.libra.po.Title
import java.sql.Timestamp

/**
 * @author 杨晓辉 2019/4/8 12:00
 */
/**
 * 用户展示班级信息
 * @param id 班级id
 * @param num 班级号
 */
data class ClassInfo(val id: Long, val num: String)

/**
 * 学生成绩信息
 * @property studentId Long 学生id
 * @property studentName String 学生名字
 * @property studentNumber String 学生学号
 * @property pageId Long 试卷id
 * @property score Double 成绩
 * @property index Int 名次
 */
class StudentAndScoreInfo {

    var studentId: Long = 0L
    lateinit var studentName: String
    lateinit var studentNumber: String
    var pageId: Long = 0L
    var score: Double = 0.0
    var index: Int = 0


    override fun toString(): String {
        return "StudentAndScoreInfo(studentId=$studentId, studentName='$studentName', studentNumber='$studentNumber', " +
                "pageId=$pageId, score=$score, index=$index)"
    }
}

/**
 * 用户详细信息
 * @property name String 名字
 * @property number String 学号/工号
 * @property userId Long 用户id
 * @property classId Long 班级id
 */
class UserProfileInfo {
    lateinit var name: String
    lateinit var number: String
    var userId: Long = 0L
    var classId: Long = 0L
    /**
     * 身份id ，student id 或者 teacher id
     */
    var identity: Long = 0L

    override fun toString(): String {
        return "UserProfileInfo(name='$name', number='$number', userId=$userId, classId=$classId)"
    }
}

/**
 * 班级学生总数
 * @property classNumber String 班级号
 * @property count Long 学生数
 * @property classId Long 班级id
 */
class ClassStudentCountInfo {
    lateinit var classNumber: String
    var classId: Long = 0L
    var membersCount: Long = 0L

    override fun toString(): String {
        return "ClassStudentCountInfo(classNumber='$classNumber', classId=$classId, count=$membersCount)"
    }
}

/**
 * 章节和知识点的信息
 */
class ChapterAndKnowledgeInfo {
    lateinit var course: Course

    lateinit var knowledges: List<Knowledge>

    override fun toString(): String {

        return "ChapterAndKnowledgeInfo(course=$course, knowledges=$knowledges)"
    }
}

/**
 * 学生返回信息
 * @property name String 学生姓名
 * @property studentNumber String 学生学号
 * @property `class` String 班级名词
 * @property classId Long 班级id
 */
class StudentInfo {
    lateinit var name: String
    lateinit var studentNumber: String
    lateinit var `class`: String
    var classId: Long = 0L
}

/**
 * 试卷详细信息
 * @property totalScores Float
 * @property teacherName String
 * @property courseName String
 * @property titles List<Title>
 * @property difficulty Float
 */
class PageInfo {
    var pageId = 0L
    var totalScores: Float = 0f
    var teacherName: String? = null
    lateinit var courseName: String
    var titles: List<Title> = emptyList()
    lateinit var paperTitle: String
    var difficulty: Float = 0f
    var selectScore: Float = 0f
    var blankScore: Float = 0f
    var answerScore: Float = 0f
    var codeScore: Float = 0f
    var algorithmScore: Float = 0f
    lateinit var createTime: String
    var knowledgeList: List<String> = emptyList()
}

/**
 * 考生试卷信息
 * @property id Long
 * @property pageId Long
 * @property classId Long
 * @property startTime Timestamp?
 * @property endTime Timestamp?
 * @property addTime Timestamp?
 * @property pageTitle String
 */
class PageAndClassInfo {
    var id: Long = 0
    var pageId: Long = 0
    var classId: Long = 0
    var startTime: Timestamp? = null
    var endTime: Timestamp? = null
    var addTime: Timestamp? = null
    lateinit var pageTitle: String
    lateinit var course: String

}

/**
 * 学生试卷信息
 * @property pageId Long
 * @property totalScores Float
 * @property teacherName String?
 * @property courseName String
 * @property paperTitle String
 * @property difficulty Float
 * @property selectScore Float
 * @property blankScore Float
 * @property answerScore Float
 * @property codeScore Float
 * @property algorithmScore Float
 * @property createTime String
 * @property knowledgeList List<String>
 */
class StudentPageInfo {
    var pageId = 0L
    var totalScores: Float = 0f
    var teacherName: String? = null
    lateinit var courseName: String
    lateinit var paperTitle: String
    var difficulty: Float = 0f
    var selectScore: Float = 0f
    var blankScore: Float = 0f
    var answerScore: Float = 0f
    var codeScore: Float = 0f
    var algorithmScore: Float = 0f
    lateinit var createTime: String
    var knowledgeList: List<String> = emptyList()
}

/**
 * 知识点和相关信息
 * @property knowledgeId Long
 * @property size Long
 */
class KnowledgeAndSize {
    var knowledgeId: Long = 0
    var size: Int = 0

    override fun toString(): String {
        return "KnowledgeAndSize(knowledgeId=$knowledgeId, size=$size)"
    }


}

/**
 * 学生平均分数信息
 * @property student StudentInfo
 * @property average [ERROR : null type]
 */
class StudentAvgInfo {
    lateinit var student: StudentInfo
    var average: Double = 0.0
    var courseId: Long = 0
}