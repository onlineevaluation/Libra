package com.nuc.evaluate.controller

import com.alibaba.fastjson.JSON
import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.vo.User
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


/**
 * @author 杨晓辉 2018-12-29 16:16
 */
private const val LOGIN_URL = "/user/login"


/**
 * 测试成功输出的json
 */
private const val SUCCESS_JSON = """{
  "data": {
    "id": 782,
    "name": "张晋霞",
    "studentNumber": "1713010101",
    "proTeamId": 0,
    "status": 2,
    "gender": null,
    "nation": null,
    "phone": null,
    "qq": null,
    "email": null,
    "idcard": null,
    "memberId": null,
    "userId": 811,
    "classId": 1
  },
  "code": 200,
  "message": "登录成功"
}"""

private const val NO_PASSWORD_JSON = """
{
  "timestamp": "2018-12-31T10:16:02.717+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "该用户不存在",
  "path": "/user/login"
}
"""

@RunWith(SpringRunner::class)
@SpringBootTest
class UserControllerTest {

    private val successUser: User = User("1713010101", "111111")
    private val noPasswordUser: User = User("1713010101", "")


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
     * 成功登录测试
     */
    @Test
    fun successLoginTest() {

        mockMvc.perform(
            MockMvcRequestBuilders.post(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(successUser))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(SUCCESS_JSON))
            .andReturn()
    }

    /**
     * 登录用户没有密码
     */
    @Test(expected = ResultException::class)
    fun failNoPasswordLoginTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(noPasswordUser))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(NO_PASSWORD_JSON))
            .andReturn()
    }
}