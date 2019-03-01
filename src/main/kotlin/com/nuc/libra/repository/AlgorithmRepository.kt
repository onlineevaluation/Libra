package com.nuc.libra.repository

import com.nuc.libra.po.Algorithm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2019/2/25 16:37
 */
@Repository
interface AlgorithmRepository : JpaRepository<Algorithm, Long>