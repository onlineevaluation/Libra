package com.nuc.evaluate.po

import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/3 15:56
 */
@Entity
@Table(name = "uek_acdemic_class")
class Clazz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var num: String? = null
    var name: String? = null
    var roomId: Long? = 0
    var majorId: Long? = 0
}