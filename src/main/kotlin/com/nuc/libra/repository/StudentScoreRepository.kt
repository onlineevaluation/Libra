package com.nuc.libra.repository

import com.nuc.libra.po.StudentScore
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author 杨晓辉 2018/2/12 10:29
 * 学生分数
 */
interface StudentScoreRepository : JpaRepository<StudentScore, Long> {

    /**
     * 通过学生id查找学生分数
     * @param studentId 学生id
     */
    fun findByStudentId(studentId: Long): List<StudentScore>?

    /**
     * 通过试卷id和学生id查找
     * @param pagesId 试卷id
     * @param studentId 学生id
     */
    fun findByPagesIdAndStudentId(pagesId: Long, studentId: Long): StudentScore?


    /**
     * 通过 pageId 获取所有的参加该考试分数
     * @param pageId 试卷id
     */
    fun findStudentScoresByPagesId(pageId: Long): List<StudentScore>?
}