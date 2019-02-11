package com.nuc.libra.jni

import com.sun.jna.Library
import com.sun.jna.Native

/**
 * @author 杨晓辉 2019-02-11 10:21
 * 摩羯座调用接口
 */
interface Capricornus : Library {


    /**
     * @param filePath 要编译的文件路径
     * @param outputPath 输入文件路径
     * @param fileName 输出文件名称
     * @param data 输入输出数据 json 格式文件
     * @param limitTime 程序运行限制时间
     */
    fun judgeCode(
        filePath: GoString.ByValue,
        outputPath: GoString.ByValue,
        fileName: GoString.ByValue,
        data: GoString.ByValue,
        limitTime: Int
    ): String


    companion object {
        val INSTANCE =
            Native.load("./dll/libCapricornus.so", Capricornus::class.java)!!
    }
}