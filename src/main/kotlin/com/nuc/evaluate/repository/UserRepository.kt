package com.nuc.evaluate.repository

import com.nuc.evaluate.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/1 15:44
 */
@Repository
interface UserRepository : JpaRepository<User, Long>