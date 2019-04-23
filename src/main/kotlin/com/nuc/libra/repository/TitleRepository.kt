package com.nuc.libra.repository

import com.nuc.libra.po.Title
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018-03-09 9:22
 * 试卷接口
 */
@Repository
interface TitleRepository : JpaRepository<Title, Long> {

    /**
     * 通过课程id和
     * @param category String
     * @param courseId Long
     * @return List<Title>
     */
    fun findByCategoryAndCourseId(category:String,courseId:Long):List<Title>

}