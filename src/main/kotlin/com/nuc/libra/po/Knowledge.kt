package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/4/11 15:50
 */
@Entity
@Table(
    name = "uek_acdemic_chapter",
    indexes = [Index(name = "id", columnList = "id"),
        Index(name = "course_id", columnList = "courseId"),
        Index(name = "chapter_id", columnList = "chapterId")]
)
class Knowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var num: Long = 0

    lateinit var name: String

    var isDifficult: Boolean = true

    var isImportant: Boolean = true

    var video: String? = null

    var courseId: Long = 0L

    var clickNum: Long = 0L

    var chapterId: Long = 0L

    override fun toString(): String {
        return "Knowledge(id=$id, num=$num, name='$name', isDifficult=$isDifficult, isImportant=$isImportant, video=$video, courseId=$courseId, clickNum=$clickNum, chapterId=$chapterId)"
    }


}