package com.nuc.libra.repository

import com.nuc.libra.po.CourseClass
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2019/4/17 17:27
 */
@Repository
interface CourseClassRepository :JpaRepository<CourseClass,Long>{
}