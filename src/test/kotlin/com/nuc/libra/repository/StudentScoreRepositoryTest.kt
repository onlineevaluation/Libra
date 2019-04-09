package com.nuc.libra.repository

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author 杨晓辉 2019/4/4 11:03
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class StudentScoreRepositoryTest {


    @Autowired
    private lateinit var studentScoreRepository: StudentScoreRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Test
    fun top10Test() {
        val list = studentScoreRepository.findTop10ScoresByPagesIdOrderByScoreDesc(1)
        if (!list.isNullOrEmpty()) {
            list.forEach {
                println(it.score)
            }
        }
    }

    /**
     *
     */
    @Test
    fun getAllStudentByStudentId() {
        val classId = 1L
        val pageId = 1L
        val studentList = studentRepository.findStudentsByClassId(classId)
        // 获取前十名的成绩
        val top10StudentScoreList = studentList.mapNotNull { student ->
            studentScoreRepository.findStudentScoresByStudentIdAndPagesId(student.id, pageId)
        }.sortedByDescending { it.score }.subList(0, 10)

        top10StudentScoreList.forEach {
            println(it.score)
        }

    }


}