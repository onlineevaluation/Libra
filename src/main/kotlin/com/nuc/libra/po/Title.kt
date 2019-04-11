package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2018-03-09 9:00
 */
@Entity
@Table(name = "uek_evaluate_titles")
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
    lateinit var answer: String
    @Column(columnDefinition = "TEXT")
    var analysis: String? = null
    var teacherId: String? = null

    @Column(columnDefinition = "TIMESTAMP")
    var addTime: String? = null
    @Column(name = "sectiona")
    var sectionA: String? = null
    @Column(name = "sectionb")
    var sectionB: String? = null
    @Column(name = "sectionc")
    var sectionC: String? = null
    @Column(name = "sectiond")
    var sectionD: String? = null
    var orderd: Boolean = true
    var knowledgeId: Long = 0

    override fun toString(): String {
        return "Title(id=$id, name='$num', title='$title', category='$category', difficulty=$difficulty, answer='$answer', analysis=$analysis, teacherId=$teacherId, addTime=$addTime, sectionA=$sectionA, sectionB=$sectionB, sectionC=$sectionC, sectionD=$sectionD, orderd=$orderd, knowledgeId=$knowledgeId)"
    }


}