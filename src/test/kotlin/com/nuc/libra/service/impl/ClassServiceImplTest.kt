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

    /**
     * 获取开始时间
     */
    @Before
    fun start() {
        startTime = System.currentTimeMillis()
    }

    /**
     * 获取学生总数
     */
    @Test
    fun countStudentTest() {
        val count = classService.studentCount(teacherId = 31)
        logger.info("count is $count")
    }

    /**
     * 班级学生总数
     */
    @Test
    fun classStudentCountTest() {
        val studentCount = classService.studentCountByClass(31)
        studentCount.map {
            logger.info(it.toString())
        }
    }

    /**
     * 班级前10测试
     */
    @Test
    fun classTop10Test() {
        val classTop10 = classService.classTop10(classId = 2, pageId = 1, teacherId = 31)
        logger.info("-----------")
        classTop10.forEach {


            logger.info("it is 排名 ${it.index} 试卷 ${it.pageId} 学号 ${it.studentNumber} score : ${it.score}")
        }
        logger.info("-----------")
    }


    /**
     * 成绩分组测试
     */
    @Test
    fun scoreAnalyticsTest() {
        val map = classService.scoreAnalytics(1, 1)
        map.forEach { (t, u) ->
            logger.info("Group $t  count: ${u.size}")
        }
    }


    /**
     * 获取班级学生数量
     */
    @Test
    fun listStudentByClassId() {
        val list = classService.listStudentScoreByClassId(classId = 2L, pageId = 3L)
        list.forEach {
            logger.info("$it")
        }
        logger.info("size is ${list.size}")
    }


    /**
     * 统计运行时间
     */
    @After
    fun finish() {
        finishTime = System.currentTimeMillis()
        logger.info("执行所花费时间：${finishTime - startTime} 毫秒")
    }
}