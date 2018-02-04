package com.nuc.evaluate.po

import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/4 15:45
 */
@Entity
@Table(name = "tg_evaluate_class_exam")
class ClassAndExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Column(name = "class_id")
    var classId = 0L
    @Column(name = "paper_id")
    var paperId = 0L
    @Column(name = "start_time")
    var startTime: Timestamp? = null
    @Column(name = "end_time")
    var endTime: Timestamp? = null

    var invigilator: Int? = 0

    var comment: String? = null


}