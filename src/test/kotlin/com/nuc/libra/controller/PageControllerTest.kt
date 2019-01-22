package com.nuc.libra.controller

import com.alibaba.fastjson.JSON
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
 * @author 杨晓辉 2019-01-21 21:02
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class PageControllerTest {


    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    private val baseUrl = "/page"

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

    @Test
    fun getPageByClassId() {
        val classId = 1
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("$baseUrl/list/class/$classId")
        ).andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        logger.info("result is ${result.response.contentAsString}")
    }

}