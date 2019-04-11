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
    var num: Int = 0
    lateinit var status: String
    var directionId: Long = 0
    var stageId: Long = 0
    var courseId: Long = 0
    var dayId: Long = 0
    @Column(columnDefinition = "DATETIME")
    var createTime: Timestamp? = null
    var userMemberId: Long = 0
    var totalscores: Long = 0
    var companyId: Long = 0
    lateinit var name: String

    override fun toString(): String {
        return "Page(id=$id, name=$num, status='$status', directionId=$directionId, stageId=$stageId, courseId=$courseId, dayId=$dayId, createTime=$createTime, userMemberId=$userMemberId, totalscores=$totalscores, companyId=$companyId, name='$name')"
    }


}