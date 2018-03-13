package com.nuc.evaluate.po

import javax.persistence.*

/**
 * @author 杨晓辉 2018-03-09 9:00
 */
@Entity
@Table(name = "uek_evaluate_titles1")
class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    @Column(columnDefinition = "TEXT")
    var num: String = "0"
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
    var directionId: Long = 0
    var status: String? = null
    var completeTime: Long = 0
    var score: Double = 0.0
    @Column(columnDefinition = "TIMESTAMP")
    var addTime: String? = null
    var teacher: Long = 0
    @Column(columnDefinition = "ENUM")
    var stageId: Long = 0
    var courseId: Long = 0
    var daysId: Long = 0
    var gongsiId: Long = 0
    var zhiweiId: Long = 0
    var type: String? = null
    var url: String? = null
    var sectiona: String? = null
    var sectionb: String? = null
    var sectionc: String? = null
    var sectiond: String? = null
    var orderd: Boolean = true

    override fun toString(): String {
        return "Title(id=$id, num=$num, title=$title, category=$category, difficulty=$difficulty, answer=$answer, analysis=$analysis, directionId=$directionId, status=$status, completeTime=$completeTime, score=$score, addTime=$addTime, teacher=$teacher, stageId=$stageId, courseId=$courseId, daysId=$daysId, gongsiId=$gongsiId, zhiweiId=$zhiweiId, type=$type, url=$url, sectiona=$sectiona, sectionb=$sectionb, sectionc=$sectionc, sectiond=$sectiond, orderd=$orderd)"
    }


}