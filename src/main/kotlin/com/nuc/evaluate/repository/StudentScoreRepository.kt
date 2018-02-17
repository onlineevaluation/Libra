package com.nuc.evaluate.repository

import com.nuc.evaluate.po.StudentScore
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author 杨晓辉 2018/2/12 10:29
 */
interface StudentScoreRepository : JpaRepository<StudentScore, Long> {

    fun findByStudentId(studentId: Long): List<StudentScore>?

    fun findByPagesIdAndStudentId(pagesId: Long, studentId: Long): StudentScore?
}