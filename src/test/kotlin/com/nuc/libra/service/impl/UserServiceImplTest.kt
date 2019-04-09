package com.nuc.libra.service.impl

import com.nuc.libra.service.ClassService
import com.nuc.libra.service.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


/**
 * @author 杨晓辉 2019/4/9 11:39
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class UserServiceImplTest {


    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun userProfile() {

        val studentId = 811L
        val teacherId = 1555L

        val profile = userService.profile(teacherId)


        logger.info(profile.toString())
    }

}