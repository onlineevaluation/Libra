package com.nuc.evaluate.repository

import com.nuc.evaluate.po.StudentAnswer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/7 11:07
 */
@Repository
interface StudentAnswerRepository : JpaRepository<StudentAnswer, Long> {
    fun findByStudentIdAndPagesId(studentId: Long, pagesId: Long): Set<StudentAnswer>
}