package com.nuc.libra.controller

import com.alibaba.fastjson.JSON
import com.nuc.libra.vo.ClassAndPageParam
import org.junit.After
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
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


/**
 * @author 杨晓辉 2019/4/8 12:27
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class ClassControllerTest {


    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    private val baseUrl = "/class"

    @Autowired
    private lateinit var wac: WebApplicationContext

    private lateinit var mockMvc: MockMvc
    private lateinit var result: MvcResult

    private val classAndPageParam = ClassAndPageParam(31, 1, 1)


    /**
     * 参数初始化
     */
    @Before
    fun initParams() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    @Test
    fun top10Test() {
        result = mockMvc.perform(
            MockMvcRequestBuilders.get("$baseUrl/top10")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(classAndPageParam))
        )
            .andExpect(MockMvcResultMatchers.status().isOk).andReturn()
    }

    @After
    fun loggerInfo() {
        logger.info("result is ${result.response.contentAsString}")
    }
}