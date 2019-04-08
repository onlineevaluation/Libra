package com.nuc.libra.repository

import com.nuc.libra.po.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/4 10:00
 */
@Repository
interface PagesRepository : JpaRepository<Page, Long>{


}