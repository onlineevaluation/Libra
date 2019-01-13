package com.nuc.evaluate.po

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import javax.persistence.*

/**
 * @author 杨晓辉 2019-01-02 14:29
 */
@Entity
@Table(name = "uek_privilege_user")
@JsonIgnoreProperties(value = ["id"])
class User : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    private lateinit var username: String

    private lateinit var password: String

    var status: Long = 0L


    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "uek_privilege_user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    private lateinit var  roles: List<Role>

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }


    override fun isAccountNonLocked(): Boolean {
        return true
    }


    override fun isCredentialsNonExpired(): Boolean {
        return true
    }


    override fun isEnabled(): Boolean {
        return true
    }

    fun setUsername(username: String) {
        this.username = username
    }

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }
}
