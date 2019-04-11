package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/4/11 15:27
 */
@Entity
@Table(
    name = "uek_acdemic_chapter",
    indexes = [Index(name = "id", columnList = "id"), Index(name = "course_id", columnList = "courseId")]
)
class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var num: Long? = 0L

    lateinit var name: String

    var isDifficult: Boolean = true

    var isImportant: Boolean = true

    var courseId: Long = 0L

    override fun toString(): String {
        return "Chapter(id=$id, num=$num, name='$name', isDifficult=$isDifficult, isImportant=$isImportant, courseId=$courseId)"
    }


}