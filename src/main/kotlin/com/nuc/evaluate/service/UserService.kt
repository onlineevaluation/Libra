package com.nuc.evaluate.service

import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.po.User


/**
 * @author 杨晓辉 2018/2/1 15:48
 */
interface UserService {

    fun findUser(): List<User>

    /**
     * 进行用户注册保存
     * @param user 用户
     * @return User 用户 包含用户信息
     * @throws ResultException 当用户名称重复抛出该异常
     *
     */
    @Throws(ResultException::class)
    fun saveUser(user: User): User

    /**
     * 通过用户名进行用户查找
     * @param user 用户
     * @return user 返回用户
     * @throws ResultException 当用户名称和密码不一致
     */
    @Throws(ResultException::class)
    fun login(user: User): User
}