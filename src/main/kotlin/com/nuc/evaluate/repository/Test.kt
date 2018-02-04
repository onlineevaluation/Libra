package com.nuc.evaluate.repository

import com.nuc.evaluate.po.PagesAndTitle
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author 杨晓辉 2018/2/4 10:22
 */
interface Test : JpaRepository<PagesAndTitle, Long> {

    fun findByPagesId(pagesId: Long): List<PagesAndTitle>

}
