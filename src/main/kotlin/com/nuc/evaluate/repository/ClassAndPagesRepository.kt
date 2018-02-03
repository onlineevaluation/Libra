package com.nuc.evaluate.repository

import com.nuc.evaluate.po.ClassAndPages
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author 杨晓辉 2018/2/3 16:00
 */
interface ClassAndPagesRepository : JpaRepository<ClassAndPages, Long> {
    fun findByClassId(classId: Long): List<ClassAndPages>
}