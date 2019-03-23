package com.nuc.libra.po

import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 2019/3/23 16:43
 */
@Entity
@Table(name = "uek_evaluate_wrong_titles")
class WrongTitles {

    /**
     * id 数据库id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var studentId: Long = 0L

    var titleId: Long = 0L

    @Column(columnDefinition = "TEXT")
    lateinit var wrongAnswer: String

    lateinit var time: Timestamp

}