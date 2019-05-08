package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/5/7 8:21
 */
@Entity
@Table(name = "uek_comment")
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var userId: Long = 0
    var videoId: Long = 0
    var userName: String = ""
    var commentTime: String? = null
    var content: String = ""

}