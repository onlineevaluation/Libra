package com.nuc.evaluate.controller

import com.nuc.evaluate.result.Result
import com.nuc.evaluate.service.UserService
import com.nuc.evaluate.util.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2018/2/1 15:47
 */
@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/listUser")
    fun listUser(): Result {
        return ResultUtils.success(200, "查询成功", userService.findUser())
    }


}