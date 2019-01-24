package com.nuc.libra.controller

import com.alibaba.fastjson.JSON
import com.nuc.libra.vo.UserParam
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.util.NestedServletException


/**
 * @author 杨晓辉 2018-12-29 16:16
 */
private const val LOGIN_URL = "/user/login"

private const val NO_PASSWORD_JSON = """
{
  "timestamp": "2018-12-31T10:16:02.717+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "该用户不存在",
  "path": "/user/login"
}
"""

/**
 * 用户用户中心测试
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("ci")
class UserControllerTest {


    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    private val successUserParam: UserParam = UserParam("1713010101", "111111")
    private val noPasswordUserParam: UserParam = UserParam("1713010101", "")
    private val passwordErrorUserParam: UserParam = UserParam("1713010101", "123456")

    @Autowired
    private lateinit var wac: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    /**
     * 参数初始化
     */
    @Before
    fun initParams() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }


    /**
     * 登录用户没有密码
     */
    @Test(expected = IllegalStateException::class)
    fun failNoPasswordLoginTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post(NO_PASSWORD_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(noPasswordUserParam))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(NO_PASSWORD_JSON))
            .andReturn()
    }

    /**
     * 成功登录测试
     */
    @Test()
    fun successLoginTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(successUserParam))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    /**
     * 用户密码错误登录
     */
    @Test(expected = NestedServletException::class)
    fun passwordErrorLoginTest() {
        val result = mockMvc.perform(
            MockMvcRequestBuilders.post(LOGIN_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(passwordErrorUserParam))
        ).andExpect(MockMvcResultMatchers.status().isOk).andReturn()
        logger.info(result.response.errorMessage)
    }
}