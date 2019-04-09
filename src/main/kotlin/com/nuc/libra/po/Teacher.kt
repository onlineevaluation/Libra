package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/4/3 16:45
 */
@Entity
@Table(
    name = "uek_acdemic_teacher",
    indexes = [Index(name = "id", columnList = "id"),
        Index(name = "job_number", columnList = "jobNumber"),
        Index(name = "user_id", columnList = "userId")]
)
class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    lateinit var jobNumber: String

    lateinit var name: String

    lateinit var hiredata: String

    var status: Int = 0

    var positionId: Long = 0L

    var userId: Long = 0L

    /**
     * 性别 1 为 男  0 为 女
     */
    var sex = 0

    var tel: String? = null

    var photo: String? = null

}