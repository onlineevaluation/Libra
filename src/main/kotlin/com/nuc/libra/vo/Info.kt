package com.nuc.libra.vo

import com.nuc.libra.po.Course
import com.nuc.libra.po.Knowledge

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


class ChapterAndKnowledgeInfo {
    lateinit var course: Course

    lateinit var knowledges: List<Knowledge>

    override fun toString(): String {

        return "ChapterAndKnowledgeInfo(course=$course, knowledges=$knowledges)"
    }


}