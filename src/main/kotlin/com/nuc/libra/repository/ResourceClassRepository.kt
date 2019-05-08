package com.nuc.libra.repository

import com.nuc.libra.po.ResourceClass
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2019/5/8 19:20
 */
@Repository
interface ResourceClassRepository : JpaRepository<ResourceClass, Long> {

    /**
     * 通过用户
     * @param resourceId Long
     * @param classId Long
     * @return ResourceClass
     */
    fun findByResourceIdAndClassId(resourceId: Long, classId: Long): ResourceClass
}