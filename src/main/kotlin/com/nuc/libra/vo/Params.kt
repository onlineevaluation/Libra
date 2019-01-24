package com.nuc.libra.vo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author 杨晓辉 2018-12-29 16:06
 * 所有的 `vo` 类
 */

/**
 * 用于接收页面的 `UserParam` 值
 * @param username 用户名
 * @param password 密码
 */
@ApiModel("登录接收数据")
data class UserParam(
    @ApiModelProperty(
        name = "username",
        value = "学号",
        required = true
    ) val username: String,

    @ApiModelProperty(
        name = "password",
        value = "密码",
        required = true
    )
    val password: String
)

@ApiModel("考试试卷信息")
data class ExamParam(
    @ApiModelProperty(
        name = "classId",
        value = "班级id",
        required = true
    )
    val classId: Long,
    @ApiModelProperty(
        name = "pageId",
        value = "试卷id",
        required = true
    )
    val pageId: Long
)