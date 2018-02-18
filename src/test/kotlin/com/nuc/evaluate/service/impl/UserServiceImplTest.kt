package com.nuc.evaluate.service.impl

import com.nuc.evaluate.po.User
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author 杨晓辉 2018/2/18 10:31
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceImplTest {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var userServiceImpl: UserServiceImpl

    lateinit var user: User

    @Before
    fun tearDown() {
        user = User()
        user.username = "1514010632"
        user.password = "111111"
    }

    @Test
    fun loginTest() {
        val user = userServiceImpl.login(user)
        Assert.assertNotNull(user)
    }

    @Test
    fun getAllUser() {
        val userList = userServiceImpl.findUser()
        logger.info("user ${userList.toString()}")
    }

}