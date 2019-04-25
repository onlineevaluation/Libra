package com.nuc.libra.task

import com.nuc.libra.repository.StudentAnswerRepository
import com.nuc.libra.repository.TitleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlin.random.Random

/**
 * @author 杨晓辉 2019/4/24 20:01
 * 计算试题难度
 */
@Component
class CalTitleDifficulty {

    @Autowired
    private lateinit var titleRepository: TitleRepository

    @Autowired
    private lateinit var answerRepository: StudentAnswerRepository

    /**
     * 定时执行难度计算任务
     * 1000 毫秒 * 60 * 60 * 24 * 7
     *           一分钟 一小时 一天 一周
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24 * 7)
    fun calDifficultyTask() {
        val allTitles = titleRepository.findAll()
        allTitles.forEach {
            val answerList = answerRepository.findByTitleId(it.id)
            val sum = answerList.sumByDouble { it.score }
            var score = 0.0
            // 难度
            when (it.category) {
                "1" -> {
                    score = 5.0
                }
                "2" -> {
                    score = 5.0
                }
                "3" -> {
                    score = 10.0
                }
                "4" -> {
                    score = 10.0
                }
            }
            var diff = sum / answerList.size / score

            // 如果难度无法计算的时候，将试题难度进行随机数，保证试题有机会在之后出现
            if (diff.equals(Double.NaN)) {
                diff = Random.nextDouble()
            }
            diff = String.format("%.2f", diff).toDouble()
            it.difficulty = diff
            // update
            titleRepository.save(it)
        }
    }
}