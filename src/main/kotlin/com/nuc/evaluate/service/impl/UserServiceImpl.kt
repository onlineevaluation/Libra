package com.nuc.evaluate.service.impl

import com.nuc.evaluate.entity.User
import com.nuc.evaluate.repository.UserRepository
import com.nuc.evaluate.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2018/2/1 15:46
 */
@Service
class UserServiceImpl:UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun findUser(): List<User> {
        return userRepository.findAll()
    }

}
