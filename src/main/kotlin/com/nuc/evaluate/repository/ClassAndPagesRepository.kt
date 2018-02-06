package com.nuc.evaluate.repository

import com.nuc.evaluate.po.ClassAndPages
import com.nuc.evaluate.po.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/3 16:00
 */
@Repository
interface ClassAndPagesRepository : JpaRepository<ClassAndPages, Long> {

    /**
     *
     */
    fun findByClassId(classId: Long): List<ClassAndPages>


    /**
     *
     */
    fun findByPagesIdAndClassId(pagesId: Long, classId: Long): Set<ClassAndPages>
}