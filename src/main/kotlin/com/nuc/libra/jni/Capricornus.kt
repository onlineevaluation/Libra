package com.nuc.libra.jni

import com.nuc.libra.exception.ResultException
import com.nuc.libra.util.PathUtils
import com.sun.jna.Library
import com.sun.jna.Native
import org.springframework.core.io.ClassPathResource
import org.springframework.util.ResourceUtils
import java.io.File

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
        private var path = File(ResourceUtils.getURL("classpath:").path)
        private val resourceFile = File(path.absolutePath, "bin/dll/")
        val INSTANCE =
            Native.load("$resourceFile\\libCapricornus.so", Capricornus::class.java)
                    ?: throw ResultException("无法加载 so 文件", 500)
    }
}