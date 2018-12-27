package com.nuc.evaluate.controller

import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.po.User
import com.nuc.evaluate.result.Result
import com.nuc.evaluate.service.UserService
import com.nuc.evaluate.util.ResultUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @author 杨晓辉 2018/2/1 15:47
 */
@RestController
@RequestMapping("/user")
class UserController {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


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
    @PostMapping("/login")
    fun login(@Valid @RequestBody user: User, bindingResult: BindingResult): Result {
        if (bindingResult.hasErrors()) {
            // 发生过改动
            throw ResultException(bindingResult.fieldError?.defaultMessage!!, 500)
        }
        logger.info("user: $user")
        return ResultUtils.success(200, "登录成功", userService.login(user))
    }

}