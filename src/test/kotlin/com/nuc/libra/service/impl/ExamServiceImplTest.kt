package com.nuc.libra.service.impl

import com.nuc.libra.service.ExamService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author 杨晓辉 2019/5/10 19:18
 */

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class ExamServiceImplTest {


    @Autowired
    private lateinit var examService: ExamService

    @Test
    fun classScoreTest() {
        examService.getStudentAllErrorTitleKnowledge(1, 2)
    }

}