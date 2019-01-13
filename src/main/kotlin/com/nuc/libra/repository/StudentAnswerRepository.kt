package com.nuc.libra.repository

import com.nuc.libra.po.StudentAnswer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/7 11:07
 */
@Repository
interface StudentAnswerRepository : JpaRepository<StudentAnswer, Long> {

    /**
     * 通过 studentId 和 pagesId 查找
     * @param studentId 学生id
     * @param pagesId 试卷id
     */
    fun findByStudentIdAndPagesId(studentId: Long, pagesId: Long): List<StudentAnswer>
}