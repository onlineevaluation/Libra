package com.nuc.evaluate.po.test

//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.authority.SimpleGrantedAuthority
//import org.springframework.security.core.userdetails.UserDetails

/**
 * @author 杨晓辉 2018/2/2 10:54
 */
//@Entity
//class SysUser : UserDetails {
//
//
//    @Id
//    @GeneratedValue
//    var id: Long = 0L
//
//    private lateinit var username: String
//
//    private lateinit var password: String
//
//    @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
//    lateinit var roles: List<SysRole>
//
//    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//        val auths: MutableList<GrantedAuthority> = ArrayList()
//        val roles = this.roles
//        roles.map {
//            auths.add(SimpleGrantedAuthority(it.name))
//        }
//        return auths
//    }
//
//    override fun isEnabled(): Boolean = true
//
//    override fun getUsername() = this.username
//
//    override fun isCredentialsNonExpired(): Boolean = true
//
//    override fun getPassword() = this.password
//
//    override fun isAccountNonExpired(): Boolean = true
//
//    override fun isAccountNonLocked(): Boolean = true
//}

