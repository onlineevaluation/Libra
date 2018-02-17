package com.nuc.evaluate.repository

import com.nuc.evaluate.po.StudentScore
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author 杨晓辉 2018/2/12 10:29
 */
interface StudentScoreRepository : JpaRepository<StudentScore, Long> {

    /**
     * 通过学生id查找学生分数
     * @param studentId 学生id
     */
    fun findByStudentId(studentId: Long): List<StudentScore>?

    fun findByPagesIdAndStudentId(pagesId: Long, studentId: Long): StudentScore?
}