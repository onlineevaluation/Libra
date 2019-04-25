package com.nuc.libra.task

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

/**
 * @author 杨晓辉 2019/4/24 20:14
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class CalTitleDifficultyTest {
    @Autowired
    private lateinit var calTitleDifficulty: CalTitleDifficulty

    @Test
    fun diffTaskTest() {
        calTitleDifficulty.calDifficultyTask()
    }

}

