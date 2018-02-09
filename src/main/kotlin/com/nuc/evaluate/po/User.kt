package com.nuc.evaluate.po

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.annotations.ApiParam
import org.hibernate.validator.constraints.NotEmpty
import javax.persistence.*
import javax.validation.constraints.NotNull

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

    @NotEmpty(message = "账号不能为空")
    lateinit var username: String
    @NotEmpty(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    lateinit var password: String

    @ApiParam(hidden = true)
    var status: Long = 0

    override fun toString(): String {
        return "User(id=$id, username='$username', password='$password', status=$status)"
    }


}