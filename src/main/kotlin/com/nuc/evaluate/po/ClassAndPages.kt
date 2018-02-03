package com.nuc.evaluate.po

import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/3 15:59
 */
@Entity
@Table(name = "tg_evaluate_class_pages")
class ClassAndPages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var pagesId: Long = 0
    var classId: Long = 0
    @Column(columnDefinition = "DATETIME")
    var startTime: String? = null
    @Column(columnDefinition = "DATETIME")
    var endTime: String? = null
    var invigilator: Long? = 0
    var comment: String? = null
    @Column(columnDefinition = "DATETIME")
    var addTime: String? = null
    var employeeId: Long? = 0
}