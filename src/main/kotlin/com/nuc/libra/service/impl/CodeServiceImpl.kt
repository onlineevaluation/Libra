package com.nuc.libra.service.impl

import com.nuc.libra.jni.Capricornus
import com.nuc.libra.jni.GoString
import com.nuc.libra.service.CodeService

/**
 * @author 杨晓辉 2019-02-11 10:34
 */
class CodeServiceImpl : CodeService {

    /**
     * @param code 学生运行代码
     */
    override fun runCode(code: String): String {

        // 文件存储

        //获取数据库答案

        val judgeCode = Capricornus.INSTANCE.judgeCode(
            GoString.ByValue(""),
            GoString.ByValue(""),
            GoString.ByValue(""),
            GoString.ByValue(""),
            20
        )
        // 判断 judge code


        return ""
    }


}