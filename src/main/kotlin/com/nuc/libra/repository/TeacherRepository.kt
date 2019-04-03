package com.nuc.libra.repository

import com.nuc.libra.po.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2019/4/3 16:52
 */
/**
 * 教师查询
 */
@Repository
interface TeacherRepository : JpaRepository<Teacher, Long> {

    /**
     * 通过 studentNumber 查找
     * @param jobNumber 工号
     */
    fun findTeacherByJobNumber(jobNumber: String): Teacher?
}