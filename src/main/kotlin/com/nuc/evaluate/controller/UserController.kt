package com.nuc.evaluate.controller

import com.nuc.evaluate.result.Result
import com.nuc.evaluate.service.UserService
import com.nuc.evaluate.util.ResultUtils
import com.nuc.evaluate.vo.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author 杨晓辉 2018/2/1 15:47
 */
/**
 * 用户中心请求
 */
@RestController
@RequestMapping("/user")
class UserController {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
//
//    @Autowired
//    private lateinit var authService: AuthService

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
     * @param userParam user参数
     * @return 返回结果
     */
//    @Throws(ResultException::class)
//    @PostMapping("/login")
//    fun login(@RequestBody userParam: com.nuc.evaluate.vo.User): Result {
////        val token = authService.login(userParam.username, userParam.password)
////        return ResultUtils.success(200, "登录成功", token)
//        return ResultUtils.success()
//    }

    @PostMapping("/login")
    fun login(@RequestBody user: User): Result {
        val token = userService.login(user.username, user.password)
        return ResultUtils.success(200,"登录成功",token)
    }


}