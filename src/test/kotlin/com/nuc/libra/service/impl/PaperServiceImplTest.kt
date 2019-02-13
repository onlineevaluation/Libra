package com.nuc.libra.service.impl

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author 杨晓辉 2019-02-13 13:35
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class PaperServiceImplTest {

    @Autowired
    private lateinit var pageService: PaperServiceImpl

    @Test
    fun getRankList() {
        val studentId = 811L

        val list = pageService.listScore(studentId)

        list.forEach {
            println("试卷 ${it.pagesId} / ${it.pageTitle} 班级排名 ${it.classRank} 年级排名 ${it.gradeRank} 分数 ${it.score}")

        }
    }

}