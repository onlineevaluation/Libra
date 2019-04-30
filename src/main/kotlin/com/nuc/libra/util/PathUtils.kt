package com.nuc.libra.util

import org.springframework.util.ResourceUtils
import java.io.File

/**
 * @author 杨晓辉 2019/4/11 16:45
 */
object PathUtils {

    /**
     * 获取项目根路径
     * @return String 根路径
     */
    fun rootPath(): String {
        var path = File(ResourceUtils.getURL("classpath:").path)
        if (!path.exists()) {
            path = File("")
        }
        return path.absolutePath + "app/"
    }
}