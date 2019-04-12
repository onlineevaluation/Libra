package com.nuc.libra.repository

import com.nuc.libra.po.CourseAndTeacher
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author 杨晓辉 2019/4/12 20:23
 */
interface CourseAndTeacherRepository : JpaRepository<CourseAndTeacher, Long> {

    /**
     * 通过教师id查询所教授的课程
     * @param teacherId Long
     * @return List<CourseAndTeacher>
     */
    fun findByTeacherId(teacherId: Long): List<CourseAndTeacher>

}