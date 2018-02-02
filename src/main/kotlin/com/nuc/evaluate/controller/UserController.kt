package com.nuc.evaluate.controller

import com.nuc.evaluate.entity.User
import com.nuc.evaluate.result.Result
import com.nuc.evaluate.service.UserService
import com.nuc.evaluate.util.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author 杨晓辉 2018/2/1 15:47
 */
@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    /**
     * 用于查询所有用户
     */
    @GetMapping("/listUser")
    fun listUser(): Result {
        return ResultUtils.success(200, "查询成功", userService.findUser())
    }

    /**
     * 用于用户注册
     */
    @PostMapping("/register")
    fun register(@RequestBody user: User): Result {
        return ResultUtils.success(200, "注册成功", userService.saveUser(user))
    }

    /**
     * 用户登录
     */
    @GetMapping("/login")
    fun login(user: User): Result {
        return ResultUtils.success(200, "登录成功", userService.login(user))
    }

}