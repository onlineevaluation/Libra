package com.nuc.libra.task

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author 杨晓辉 5/3/2019 6:53 PM
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class RecommendTaskTest{


    @Autowired
    private lateinit var recommendTask: RecommendTask

    @Test
    fun recommendTest() {
        recommendTask.recommendResource()
    }
}