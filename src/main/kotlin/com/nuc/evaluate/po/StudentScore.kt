package com.nuc.evaluate.po

import java.sql.Date
import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/12 10:26
 */

@Entity
@Table(name = "uek_evaluate_student_score")
class StudentScore {

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
}