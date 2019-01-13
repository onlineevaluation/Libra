package com.nuc.libra.util

import com.nuc.libra.result.Result

/**
 * @author 杨晓辉 2018/2/1 14:07
 * ResultUtils 通过该类进行结果返回，保证了接口风格统一
 */
object ResultUtils {

    private var result = Result()

    /**
     * 返回结果成功
     * @param code 返回状态码，默认为 200 表示成功
     * @param message 状态信息，默认为成功
     * @param data 返回数据，默认为 `null`
     *
     */
    fun success(code: Int = 200, message: String = "成功", data: Any? = null): Result {
        result.code = code
        result.message = message
        result.data = data
        return result
    }

    /**
     * 返回错误信息
     * @param code 错误状态码
     * @param message 错误信息
     */
    fun error(code: Int, message: String): Result {
        result.code = code
        result.message = message
        result.data = null
        return result
    }
}