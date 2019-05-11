package com.nuc.libra.repository

import com.nuc.libra.po.StudentAnswer
import com.nuc.libra.po.Title
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


    /**
     * 通过试题id查找答案
     * @param titleId Long
     * @return List<StudentAnswer> 返回学生答案
     */
    fun findByTitleId(titleId: Long): List<StudentAnswer>


    /**
     * 通过学生id获取所有的学生
     * @return List<StudentAnswer>
     */
    fun findByStudentId(studentId: Long): List<StudentAnswer>

    /**
     * 通过学生id 试题id 试卷id 查找
     * @param studentId Long
     * @param titleId Long
     * @param pagesId Long
     * @return StudentAnswer
     */
    fun findByStudentIdAndTitleIdAndPagesId(studentId: Long, titleId: Long, pagesId: Long): StudentAnswer?
}