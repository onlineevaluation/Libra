package com.nuc.evaluate.po

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.annotations.ApiParam
import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/1 15:38
 * 用户表
 */
@Entity
@Table(name = "uek_privilege_user")
@JsonIgnoreProperties(value = ["id"])
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiParam(hidden = true)
    var id: Long = 0L

    lateinit var username: String

    lateinit var password: String

    @ApiParam(hidden = true)
    var status: Long = 0

}