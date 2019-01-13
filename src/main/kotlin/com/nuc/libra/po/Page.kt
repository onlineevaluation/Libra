package com.nuc.libra.po

import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/3 11:10
 */
@Entity
@Table(name = "uek_evaluate_pages")
class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
    @Column(columnDefinition = "INT")
    var num: String? = null
    var status: String? = null
    var directionId: Long = 0
    var stageId: Long = 0
    var courseId: Long = 0
    var dayId: Long = 0
    @Column(columnDefinition = "DATETIME")
    var createTime: Timestamp? = null
    @Column(columnDefinition = "ENUM")
    var type: String? = null
    @Column(columnDefinition = "ENUM")
    var pagesType: String? = null
    var userMemberId: Long = 0
    var totalscores: Long = 0
    var companyId: Long = 0
}