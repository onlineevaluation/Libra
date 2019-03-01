package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.UserService
import com.nuc.libra.util.ResultUtils
import com.nuc.libra.vo.UserParam
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author 杨晓辉 2018/2/1 15:47
 * 用户中心请求
 */
@Api(tags = ["用户中心"], description = "用户中心操作接口")
@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService


    /**
     * 用户登录
     * @param userParam user参数
     * @return 返回 token 包含 用户名 用户id 学生所在班级
     */
    @ApiOperation("登录授权方法", httpMethod = "POST")
    @PostMapping("/login")
    fun login(@RequestBody userParam: UserParam): Result {
        val token = userService.login(userParam.username, userParam.password)
        return ResultUtils.success(200, "登录成功", token)
    }

    /**
     * @param id 用户id
     * 通过用户id获取详细信息
     */
    @GetMapping("/profile/{id}")
    fun userProfile(@PathVariable id: Long): Result {
        val profile = userService.profile(id)
        return ResultUtils.success(data = profile)
    }


}