package com.nuc.libra.service.impl

import com.nuc.libra.exception.ResultException
import com.nuc.libra.po.Role
import com.nuc.libra.po.User
import com.nuc.libra.po.UserAndRole
import com.nuc.libra.repository.RoleRepository
import com.nuc.libra.repository.StudentRepository
import com.nuc.libra.repository.UserAndRoleRepository
import com.nuc.libra.repository.UserRepository
import com.nuc.libra.security.JwtTokenProvider
import com.nuc.libra.service.UserService

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * @author 杨晓辉 2018/2/1 15:46
 * 用户信息中心
 */
@Service
class UserServiceImpl : UserService, UserDetailsService {


    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userAndRoleRepository: UserAndRoleRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var roleRepository: RoleRepository

    /**
     * 获得所有的用户
     */
    override fun findUser(): List<User> {
        return userRepository.findAll()
    }


    /**
     * 通过用户名进行用户查找
     * @param username 用户学号
     * @param password 用户密码
     * @return token 信息
     * @throws ResultException 当用户名称和密码不一致
     */
    override fun login(username: String, password: String): String {
        logger.info("user name is $username and password is $password")
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        val user = userRepository.findUserByUsername(username) ?: throw ResultException("没有该用户", 500)
        val userAndRole = userAndRoleRepository.findUserAndRoleByUserId(user.id)
        val role = roleRepository.findById(userAndRole.roleId).get()
        // 获得用户角色
        val authList = ArrayList<Role>()
        authList.add(role)
        val student = studentRepository.findByStudentNumber(user.username) ?: throw ResultException("用户查询失败", 500)
        return jwtTokenProvider.createToken(authList,student)
    }

    /**
     * @param username 学号
     */
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findUserByUsername(username)
                ?: throw UsernameNotFoundException("UserParam '$username' not found")

        return org.springframework.security.core.userdetails.User//
            .withUsername(username)//
            .password(user.password)//
            .authorities("STU")//
            .accountExpired(false)//
            .accountLocked(false)//
            .credentialsExpired(false)//
            .disabled(false)//
            .build()
    }
}

