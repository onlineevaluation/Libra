package com.nuc.libra.controller

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


/**
 * @author 杨晓辉 2018-12-31 16:57
 */

private const val INDEX_URL = "/"
private const val SUCCESS_JSON = """{
  "data": null,
  "code": 200,
  "message": "swagger-ui.html"
}"""

@RunWith(SpringRunner::class)
@SpringBootTest
class IndexControllerTest {

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
     * 测试Index请求
     */
    @Test
    fun successIndex() {
        mockMvc.perform(
            MockMvcRequestBuilders.post(INDEX_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(SUCCESS_JSON))
            .andReturn()
    }
}