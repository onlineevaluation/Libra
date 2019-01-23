package com.nuc.libra.po

import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/1 16:30
 * 角色表
 */
@Entity
@Table(name = "uek_privilege_role")
class Role : GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    lateinit var name: String

    lateinit var about: String


    override fun getAuthority(): String {
        return name
    }

    override fun toString(): String {
        return "Role(id=$id, name='$name', about='$about')"
    }


}