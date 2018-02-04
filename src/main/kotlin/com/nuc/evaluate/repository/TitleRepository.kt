package com.nuc.evaluate.repository

import com.nuc.evaluate.po.Title
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/4 10:12
 */
@Repository
interface TitleRepository : JpaRepository<Title, Long> {
}