package com.nuc.libra.repository

import com.nuc.libra.po.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/1 15:44
 * 用户登录
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
    /**
     * 通过用户名和密码进行用户查询
     *
     * @param username 用户名
     * @param password 用户密码
     * @return user 可能为空
     */
    fun findByUsernameAndPassword(username: String, password: String): User?

    /**
     * 通过用户名查找用户
     * @param username 用户学号
     */
    fun findUserByUsername(username: String):User?
}