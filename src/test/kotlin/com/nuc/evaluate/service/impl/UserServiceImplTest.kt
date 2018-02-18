package com.nuc.evaluate.service.impl

import com.nuc.evaluate.po.User
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author 杨晓辉 2018/2/18 10:31
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    lateinit var userServiceImpl: UserServiceImpl

    lateinit var user: User

    @Before
    fun tearDown() {
        user = User()
        user.username = "1514010631"
        user.password = "111111"
    }

    @Test
    fun loginTest() {
        val user = userServiceImpl.login(user)
        Assert.assertNotNull(user)
    }

}