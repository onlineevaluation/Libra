package com.nuc.evaluate.po

import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/4 10:08
 */
@Entity
@Table(name = "tg_evaluate_recruit_titles")
class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    @Column(columnDefinition = "TEXT")
    lateinit var num: String
    @Column(columnDefinition = "TEXT")
    lateinit var title: String
    @Column(columnDefinition = "ENUM")
    lateinit var category: String
    @Column(columnDefinition = "ENUM")
    var difficulty: String? = null
    @Column(columnDefinition = "TEXT")
    var answer: String? = null
    @Column(columnDefinition = "TEXT")
    var analysis: String? = null
    var teacher: Long? = 0
    @Column(columnDefinition = "TIMESTAMP")
    var addTime: java.sql.Timestamp? = null
    var score: Long = 0
    var completeTime: Long = 0
    @Column(columnDefinition = "ENUM")
    var status: String? = null
    var directionId: Long = 0
    var stageId: Long = 0
    var courseId: Long = 0
    var daysId: Long = 0
    var gongsiId: Long? = 0
    var zhiweiId: Long? = 0
    var type: String? = null
    var url: String? = null


}