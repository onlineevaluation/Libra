package com.nuc.libra.repository

import com.nuc.libra.po.Class
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author 杨晓辉 2019/4/3 18:17
 */
interface ClassRepository : JpaRepository<Class, Long> {


}