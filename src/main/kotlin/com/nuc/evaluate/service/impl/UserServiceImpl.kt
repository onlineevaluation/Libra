package com.nuc.evaluate.service.impl

import com.nuc.evaluate.po.UserAndRole
import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.po.Student
import com.nuc.evaluate.po.User
import com.nuc.evaluate.repository.StudentRepository
import com.nuc.evaluate.repository.UserAndRoleRepository
import com.nuc.evaluate.repository.UserRepository
import com.nuc.evaluate.service.UserService
import com.nuc.evaluate.util.Md5Utils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * @author 杨晓辉 2018/2/1 15:46
 */
@Service
class UserServiceImpl : UserService {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userAndRoleRepository: UserAndRoleRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

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
            .forEach({ throw ResultException("学号重复", 500) })
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
     * @param user 用户
     * @return userDTO 返回用户
     * @throws ResultException 当用户名称和密码不一致
     */
    @Throws(ResultException::class)
    override fun login(user: User): Student {

        val student = studentRepository.findByStudentNumber(user.username) ?: throw ResultException("该用户不存在", 500)
        val userInDb = userRepository.findOne(student.userId)
        logger.info("userInDb : $userInDb")
        logger.info("userInDb password : ${userInDb.password}")
        logger.info("user password : ${Md5Utils.md5(user.password)}")
        if (userInDb.password != Md5Utils.md5(user.password)) {
            throw ResultException("用户密码错误", 500)
        }
        return student
    }

}
