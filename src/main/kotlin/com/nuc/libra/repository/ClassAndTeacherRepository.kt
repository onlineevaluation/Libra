package com.nuc.libra.repository

import com.nuc.libra.po.ClassAndTeacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2019/4/3 18:10
 */
@Repository
interface ClassAndTeacherRepository : JpaRepository<ClassAndTeacher, Long> {

    /**
     * @param teacherId 教师 id
     * 
     */
    fun findClassAndTeachersByTeacherId(teacherId: Long): List<ClassAndTeacher>?

}