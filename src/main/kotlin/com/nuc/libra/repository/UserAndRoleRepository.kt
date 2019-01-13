package com.nuc.libra.repository

import com.nuc.libra.po.UserAndRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/1 16:37
 * 用户和角色对应表
 */
@Repository
interface UserAndRoleRepository : JpaRepository<UserAndRole, Long> {

    /**
     * 通过用户id查找
     * @param userId 用户id
     */
    fun findUserAndRoleByUserId(userId:Long):UserAndRole

}