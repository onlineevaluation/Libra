package com.nuc.libra.po

import java.sql.Date
import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/12 10:26
 */

@Entity
@Table(
    name = "uek_evaluate_student_score", indexes = [
        Index(name = "id", columnList = "id"),
        Index(name = "student_id", columnList = "studentId"),
        Index(name = "pages_id", columnList = "pagesId"),
        Index(name = "pages_id", columnList = "pagesId")
    ]
)
class StudentScore : Comparable<StudentScore> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var studentId: Long = 0
    var pagesId: Long = 0
    var objectivesScore: Long? = 0
    var subjectivityScore: Long? = 0
    var score: Double = 0.0
    var status: String? = null
    var time: Timestamp? = null
    var employeeId: Long? = 0
    var dotime: Date? = null

    override fun compareTo(other: StudentScore): Int {
        return when {
            other.score > this.score -> 1
            other.score == this.score -> 0
            else -> -1
        }
    }


    override fun toString(): String {
        return "StudentScore(id=$id, studentId=$studentId, pagesId=$pagesId, objectivesScore=$objectivesScore, subjectivityScore=$subjectivityScore, score=$score, status=$status, time=$time, employeeId=$employeeId, dotime=$dotime)"
    }


}