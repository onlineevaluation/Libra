package com.nuc.libra.po

import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/3 15:59
 * 班级 试卷 中卷表
 */
@Entity
@Table(
    name = "uek_evaluate_class_pages", indexes = [
        Index(name = "page_id", columnList = "pagesId"),
        Index(name = "class_id", columnList = "classId"),
        Index(name = "id", columnList = "id")
    ]
)
class ClassAndPages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var pagesId: Long = 0
    var classId: Long = 0
    @Column(columnDefinition = "DATETIME")
    var startTime: Timestamp? = null
    @Column(columnDefinition = "DATETIME")
    var endTime: Timestamp? = null
    var invigilator: Long? = 0
    var comment: String? = null
    @Column(columnDefinition = "TIMESTAMP")
    var addTime: Timestamp? = null
    var employeeId: Long? = 0

    var needTime = 0
}