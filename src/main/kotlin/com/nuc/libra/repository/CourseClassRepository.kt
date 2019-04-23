package com.nuc.libra.repository

import com.nuc.libra.po.CourseClass
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2019/4/17 17:27
 */
@Repository
interface CourseClassRepository : JpaRepository<CourseClass, Long> {

    /**
     * 通过教师id获取所有信息
     * @param teacherId Long 教师id
     * @return List<CourseClass>
     */
    fun findByTeacherId(teacherId: Long): List<CourseClass>

    /**
     * 通过教师id和班级id获取
     * @param teacherId Long 教师id
     * @param classId Long 班级id
     * @return List<CourseClass>?
     */
    fun findByTeacherIdAndClassId(teacherId: Long, classId: Long): List<CourseClass>?
}