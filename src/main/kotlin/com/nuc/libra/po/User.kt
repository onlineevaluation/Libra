package com.nuc.libra.po

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import javax.persistence.*

/**
 * @author 杨晓辉 2019-01-02 14:29
 */
@Entity
@Table(
    name = "uek_privilege_user",
    indexes = [Index(name = "id", columnList = "id"), Index(name = "username", columnList = "username")]
)
@JsonIgnoreProperties(value = ["id"])
class User : UserDetails {
    /**
     * id 数据库id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    /**
     * 该属性为 **学号** 或者 **工号**
     */
    private lateinit var username: String

    private lateinit var password: String

    /**
     * 状态
     */
    var status: Long = 0L


    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "uek_privilege_user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    private lateinit var roles: List<Role>

    /**
     * 获取用户名
     */
    override fun getUsername(): String {
        return username
    }

    /**
     * 账号是否超时？？
     */
    override fun isAccountNonExpired(): Boolean {
        return true
    }


    /**
     * 账号是否被锁
     */
    override fun isAccountNonLocked(): Boolean {
        return true
    }


    /**
     * 账号凭证是否？？？
     */
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    /**
     * 账号是否可用
     */
    override fun isEnabled(): Boolean {
        return true
    }

    /**
     * 设置用户名
     * @param username 学号
     */
    fun setUsername(username: String) {
        this.username = username
    }

    /**
     * 获取权限
     */
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles
    }

    /**
     * 获取密码
     */
    override fun getPassword(): String {
        return password
    }


    fun setAuthorities(roles: List<Role>) {
        this.roles = roles
    }

    /**
     * 设置密码
     * @param password 密码
     */
    fun setPassword(password: String) {
        this.password = password
    }

    override fun toString(): String {
        return "UserParam(id=$id, username='$username', password='$password', status=$status, roles=$roles)"
    }


}
