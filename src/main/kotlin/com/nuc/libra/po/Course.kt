package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019-02-15 17:55
 * 课程 表
 */
@Entity
@Table(name = "uek_acdemic_course", indexes = [Index(name = "id", columnList = "id")])
class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var num: Long? = 0L

    lateinit var name: String

    var introduce: String? = null

    var coverImage: String? = null

    override fun toString(): String {
        return "Course(id=$id, name=$num, name='$name', about=$introduce, coverImage=$coverImage)"
    }

}