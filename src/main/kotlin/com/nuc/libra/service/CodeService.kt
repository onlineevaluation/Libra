package com.nuc.libra.service

import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019-02-11 10:35
 */
@Service
interface CodeService {

    /**
     * @param code 学生代码
     */
    fun runCode(code:String):String

}