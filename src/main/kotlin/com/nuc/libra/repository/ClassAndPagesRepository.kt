package com.nuc.libra.repository

import com.nuc.libra.po.ClassAndPages
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/3 16:00
 */
@Repository
interface ClassAndPagesRepository : JpaRepository<ClassAndPages, Long> {

    /**
     * 根据班级 `id` 查找
     * @param classId 班级id
     * @return 返回list
     */
    fun findByClassId(classId: Long): List<ClassAndPages>


    /**
     * 根据试卷 id 和班级 id 进行查找
     * @param pagesId 试卷id
     * @param classId 班级id
     * @return ClassAndPages 的 set 集合
     */
    fun findByPagesIdAndClassId(pagesId: Long, classId: Long): Set<ClassAndPages>
}