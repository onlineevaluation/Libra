package com.nuc.libra.po

import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/3 11:10
 * 试卷表
 */
@Entity
@Table(name = "uek_evaluate_pages",indexes = [Index(name = "id",columnList = "id"), Index(name = "create_id",columnList = "createId")])
class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var status: String? = null
    var courseId: Long = 0
    @Column(columnDefinition = "DATETIME")
    var createTime: Timestamp? = null
    var totalScores: Float = 0f
    lateinit var paperTitle: String
    var choiceScore: Float = 0.0f
    var blankScore: Float = 0.0f
    var answerScore: Float = 0.0f
    var codeScore: Float = 0.0f
    var algorithmScore: Float = 0.0f
    var createId: Long = 0L

    override fun toString(): String {
        return "Page(id=$id, status=$status, courseId=$courseId, createTime=$createTime, totalscores=$totalScores, paperTitle='$paperTitle', choiceScore=$choiceScore, blankScore=$blankScore, answerScore=$answerScore, codeScore=$codeScore, algorithmScore=$algorithmScore, createId=$createId)"
    }


}