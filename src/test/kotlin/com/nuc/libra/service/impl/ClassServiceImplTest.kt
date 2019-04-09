package com.nuc.libra.service.impl

import com.nuc.libra.service.ClassService
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


/**
 * @author 杨晓辉 2019/4/8 13:01
 */


@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class ClassServiceImplTest {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var classService: ClassService

    private var startTime: Long = 0L
    private var finishTime: Long = 0L

    @Before
    fun start() {
        startTime = System.currentTimeMillis()
    }

    @Test
    fun countStudentTest() {
        val count = classService.studentCount(teacherId = 31)
        logger.info("count is $count")
    }


    @Test
    fun classStudentCountTest() {
        val studentCount = classService.studentCountByClass(31)
        studentCount.map {
            logger.info(it.toString())
        }
    }

    @Test
    fun classTop10Test(){
        classService.classTop10(classId = 1,pageId = 1,teacherId = 31)
    }

    @After
    fun finish() {
        finishTime = System.currentTimeMillis()
        logger.info("执行所花费时间：${finishTime - startTime} 毫秒")

    }
}