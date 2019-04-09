package com.nuc.libra.vo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.sql.Date
import java.sql.Timestamp

/**
 * @author 杨晓辉 2018-12-29 16:06
 * 所有的 `vo` 类
 */

/**
 * 用于接收页面的 `UserParam` 值
 * @param username 用户名
 * @param password 密码
 */
@ApiModel("登录接收数据")
data class UserParam(
    @ApiModelProperty(
        name = "username",
        value = "学号",
        required = true
    ) val username: String,

    @ApiModelProperty(
        name = "password",
        value = "密码",
        required = true
    )
    val password: String
)

@ApiModel("考试试卷信息")
data class ExamParam(
    @ApiModelProperty(
        name = "classId",
        value = "班级id",
        required = true
    )
    val classId: Long,
    @ApiModelProperty(
        name = "pageId",
        value = "试卷id",
        required = true
    )
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


open class StudentAnswer {
    var id: Long = 0L
    var answer: String = ""
    var score: Double = 0.0
    var standardAnswer = ""
    lateinit var title: String
}

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

