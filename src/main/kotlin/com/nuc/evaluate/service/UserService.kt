package com.nuc.evaluate.service

import com.nuc.evaluate.entity.User


/**
 * @author 杨晓辉 2018/2/1 15:48
 */
interface UserService {
    fun findUser(): List<User>
}