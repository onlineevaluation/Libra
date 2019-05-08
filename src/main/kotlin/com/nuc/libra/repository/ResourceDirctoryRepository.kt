package com.nuc.libra.repository

import com.nuc.libra.po.ResourceDirctory
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author 杨晓辉 2019/5/5 11:54
 */
interface ResourceDirctoryRepository : JpaRepository<ResourceDirctory, Long> {

    /**
     * 通过知识点获取资源
     * @param knowledgeId Long
     * @return List<ResourceDirctory>
     */
    fun findByKnowledgeIdAndType(knowledgeId: Long,type:Long): List<ResourceDirctory>


}