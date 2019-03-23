package com.nuc.libra.repository

import com.nuc.libra.po.WrongTitles
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2019/3/23 16:46
 * 错误试题
 */
@Repository
interface WrongTitlesRepository :JpaRepository<WrongTitles,Long>