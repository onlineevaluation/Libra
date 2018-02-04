package com.nuc.evaluate.repository

import com.nuc.evaluate.po.PagesAndTitle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/4 10:03
 */
@Repository
interface PageAndTitleRepository : JpaRepository<PagesAndTitle, Long> {
    fun findByPagesId(pageId: Long): List<PagesAndTitle>
}