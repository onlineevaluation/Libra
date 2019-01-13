package com.nuc.evaluate.service

import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.po.Student
import com.nuc.evaluate.po.User


/**
 * @author 杨晓辉 2018/2/1 15:48
 */
interface UserService {

    /**
     * 获取所有的用户
     */
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
     * @param username 学号
     * @param password 密码
     * @return HashMap 返回用户信息和token
     * @throws ResultException 当用户名称和密码不一致
     */
    @Throws(ResultException::class)
    fun login(username: String, password: String): HashMap<Any, Any>
}