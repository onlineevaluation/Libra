package com.nuc.libra.vo

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
 */
class StudentAndScoreInfo {

    var studentId: Long = 0L
    lateinit var studentName: String
    lateinit var studentNumber: String
    var pageId: Long = 0L
    var score: Double = 0.0


    override fun toString(): String {
        return "StudentAndScoreInfo(studentId=$studentId, studentName='$studentName', studentNumber='$studentNumber', pageId=$pageId, score=$score)"
    }
}