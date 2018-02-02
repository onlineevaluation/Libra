package com.nuc.evaluate.service.impl

import com.nuc.evaluate.entity.User
import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.repository.UserRepository
import com.nuc.evaluate.service.UserService
import com.nuc.evaluate.util.Md5Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2018/2/1 15:46
 */
@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

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
    @Throws(ResultException::class)
    override fun saveUser(user: User): User {
        val userList = findUser()
        (0 until userList.size)
                .filter { userList[it].username == user.username }
                .forEach { throw ResultException("名字重复", 500) }
        user.password = Md5Utils.md5(user.password)
        return userRepository.save(user)
    }

    /**
     * 通过用户名进行用户查找
     * @param user 用户
     * @return user 返回用户
     * @throws ResultException 当用户名称和密码不一致
     */
    @Throws(ResultException::class)
    override fun login(user: User): User {
        return userRepository.findByUsernameAndPassword(user.username, Md5Utils.md5(user.password))
                ?: throw ResultException("用户不存在或密码错误", 500)
    }

}
