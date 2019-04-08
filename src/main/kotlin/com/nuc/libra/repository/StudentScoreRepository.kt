package com.nuc.libra.repository

import com.nuc.libra.po.Student
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
     * 通过学生 id 和 试卷 id 查找学生分数,返回单个学生成绩
     * @param studentId 学生id
     * @param pageId 试卷id
     */
    fun findStudentScoresByStudentIdAndPagesId(studentId: Long,pageId: Long): StudentScore?

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

    /**
     * 通过 试卷 获取 分数最高的 10名 同学
     */
    fun findTop10ScoresByPagesIdOrderByScoreDesc(pageId: Long): List<StudentScore>?

    /**
     * 通过学生id和pageId 获取 所有的试卷
     */
    fun findAllByStudentIdAndPagesId(studentIds: Iterable<Long>, pagesId: Long): List<StudentScore>

    fun findAllByStudentId(studentIds: Iterable<Long>): List<StudentScore>

}