package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.UserService
import com.nuc.libra.util.ResultUtils
import com.nuc.libra.vo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author 杨晓辉 2018/2/1 15:47
 * 用户中心请求
 */
@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    /**
     * 用于查询所有用户
     */
    @GetMapping("/list")
    fun listUser(): Result {
        return ResultUtils.success(200, "查询成功", userService.findUser())
    }

    /**
     * 用户登录
     * @param user user参数
     * @return 返回结果
     */
    @PostMapping("/login")
    fun login(@RequestBody user: User): Result {
        val token = userService.login(user.username, user.password)
        return ResultUtils.success(200, "登录成功", token)
    }


}