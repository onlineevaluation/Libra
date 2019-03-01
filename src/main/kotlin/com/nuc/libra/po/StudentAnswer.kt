package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/7 11:03
 */
@Entity
@Table(name = "uek_evaluate_answer")
class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var studentId: Long = 0
    var pagesId: Long = 0
    var titleId: Long = 0
    @Column(columnDefinition = "TEXT")
    var answer: String = ""
    var score: Double = 0.0
    var time: java.sql.Timestamp? = null
    var employeeId: Long? = 0
    var url: String = ""
    // update date 2019年2月3日
    var note: String? = null

    // 试题相似度
    var similarScore: Double = 0.0

    override fun toString(): String {
        return "StudentAnswer(id=$id, studentId=$studentId, pagesId=$pagesId, titleId=$titleId, answer='$answer', score=$score, time=$time, employeeId=$employeeId, url='$url', similarScore=$similarScore)"
    }


}
