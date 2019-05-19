package com.nuc.libra.repository

import com.nuc.libra.po.SystemNotice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2019/5/19 20:37
 */
@Repository
interface NoticeRepository : JpaRepository<SystemNotice, Long> {

    /**
     * 通过属于类型查找通知
     * @param belongId Long
     * @return List<SystemNotice>
     */
    fun findByBelongId(belongId: String): List<SystemNotice>
}