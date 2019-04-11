package com.nuc.libra.repository

import com.nuc.libra.po.Chapter
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author 杨晓辉 2019/4/11 15:35
 */
interface ChapterRepository : JpaRepository<Chapter, Long> {

    /**
     * 通过课程id 查找所有相关知识点
     * @param courseId Long
     */
    fun findByCourseId(courseId: Long):List<Chapter>


}