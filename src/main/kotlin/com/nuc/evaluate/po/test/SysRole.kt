package com.nuc.evaluate.po.test

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * @author 杨晓辉 2018/2/2 10:53
 */
@Entity
class SysRole {
    @Id
    @GeneratedValue
    var id: Long = 0L
    lateinit var name: String

}