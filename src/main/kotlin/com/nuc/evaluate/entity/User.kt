package com.nuc.evaluate.entity

import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/1 15:38
 * 用户表
 */
@Entity
@Table(name = "uek_privilege_user")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    lateinit var username: String

    lateinit var password: String

    var status: Long = 0
}