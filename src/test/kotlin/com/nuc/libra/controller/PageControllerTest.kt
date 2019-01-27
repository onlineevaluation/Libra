package com.nuc.libra.controller

import com.alibaba.fastjson.JSON
import com.nuc.libra.vo.ExamParam
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
 * @author 杨晓辉 2019-01-21 21:02
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class PageControllerTest {


    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    private val baseUrl = "/page"

    @Autowired
    private lateinit var wac: WebApplicationContext

    private lateinit var mockMvc: MockMvc
    private lateinit var result: MvcResult
    /**
     * 参数初始化
     */
    @Before
    fun initParams() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    /**
     * 通过 class id 试卷
     *
     */
    @Test
    fun getPageByClassIdTest() {
        val classId = 1
        result = mockMvc.perform(
            MockMvcRequestBuilders.get("$baseUrl/exams/$classId")
        ).andExpect(MockMvcResultMatchers.status().isOk).andReturn()


    }

    /**
     * 通过 试卷id 和 班级 id 获取试卷信息内容
     */
    @Test
    fun getPageInfoTest() {
        val examParam = ExamParam(1, 1)
        result = mockMvc.perform(
            MockMvcRequestBuilders.get("$baseUrl/exam").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(examParam))
        ).andReturn()

    }

    /**
     * 每个测试结束时候打印测试结果
     */
    @After
    fun loggerInfo() {
        logger.info("result is ${result.response.contentAsString}")
    }

}