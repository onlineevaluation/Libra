package com.nuc.libra.repository

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author 杨晓辉 2019/4/3 17:23
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class TeacherRepositoryTest {

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Test
    fun findByJobIdTest() {
        val teacher = teacherRepository.findTeacherByJobNumber("T1514010101")
        Assert.assertEquals("杨晓辉",teacher?.name)
    }

}