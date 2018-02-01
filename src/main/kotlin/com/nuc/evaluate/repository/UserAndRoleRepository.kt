package com.nuc.evaluate.repository

import com.nuc.evaluate.entity.UserAndRole
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author 杨晓辉 2018/2/1 16:37
 */
interface UserAndRoleRepository : JpaRepository<UserAndRole, Long> {
}