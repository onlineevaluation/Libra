package com.nuc.evaluate.service.impl

import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.po.User
import com.nuc.evaluate.po.UserAndRole
import com.nuc.evaluate.repository.RoleRepository
import com.nuc.evaluate.repository.StudentRepository
import com.nuc.evaluate.repository.UserAndRoleRepository
import com.nuc.evaluate.repository.UserRepository
import com.nuc.evaluate.security.JwtTokenProvider
import com.nuc.evaluate.service.UserService
import com.nuc.evaluate.util.Md5Utils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
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
     * 进行用户注册保存
     * @param user 用户
     * @return User 用户 包含用户信息
     * @throws ResultException 当用户名称重复抛出该异常
     *
     */
    @Transactional(rollbackOn = [ResultException::class])
    @Throws(ResultException::class)
    override fun saveUser(user: User): User {
        val userList: List<User> = findUser()
        (0 until userList.size)
            .filter { userList[it].username == user.username }
            .forEach { throw ResultException("学号重复", 500) }
        user.password = Md5Utils.md5(user.password)
        val userInDB = userRepository.save(user) ?: throw ResultException("注册失败", 500)
        val userAndRole = UserAndRole()
        userAndRole.roleId = 25L
        userAndRole.userId = userInDB.id
        userAndRoleRepository.save(userAndRole)
        return userInDB
    }


    /**
     * 通过用户名进行用户查找
     * @param user 用户学号
     * @return token 信息
     * @throws ResultException 当用户名称和密码不一致
     */
    override fun login(username: String, password: String): HashMap<Any, Any> {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        val user = userRepository.findUserByUsername(username) ?: throw ResultException("没有该用户", 500)
        val userAndRole = userAndRoleRepository.findUserAndRoleByUserId(user.id)
        val role = roleRepository.findById(userAndRole.roleId).get()
        val token = jwtTokenProvider.createToken(username, role.name)
        val student = studentRepository.findByStudentNumber(user.username) ?: throw ResultException("用户查询失败", 500)
        val map = HashMap<Any, Any>()
        map["token"] = token
        map["student"] = student
        logger.info(map.toString())
        return map
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findUserByUsername(username)
                ?: throw UsernameNotFoundException("User '$username' not found")

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

