package com.nuc.libra.service.impl

import com.nuc.libra.service.RecommendService
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author 杨晓辉 2019/5/7 8:50
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class RecommendServiceImplTest {

    @Autowired
    private lateinit var recommendService: RecommendService

    @Test
    fun RecommmdServiceTest() {
        val studentId = 782L
        val rankList = recommendService.getResourceByStudentId(studentId)
        rankList.forEach {
            println(it)
        }
    }

}