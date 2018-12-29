package com.nuc.evaluate.controller

import com.nuc.evaluate.vo.User
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


/**
 * @author 杨晓辉 2018-12-29 16:16
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class UserControllerTest {

    private val LOGIN_URL = "/user/login"

    private val userSuccess: User = User("1713010101", "111111")

    @Autowired
    private lateinit var wac: WebApplicationContext

    private lateinit var mockMvc: MockMvc


    @Before
    fun initParams() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    /**
     * 成功登录测试
     */
    @Test
    fun successLoginTest() {
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL))
        println("result Action is ${resultActions.toString()}")
    }
}