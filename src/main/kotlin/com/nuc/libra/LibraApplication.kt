package com.nuc.libra

import com.nuc.libra.util.PathUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

/**
 * spring boot 启动入口
 */
@SpringBootApplication
class LibraApplication
private val logger: Logger = LoggerFactory.getLogger(LibraApplication::class.java)

/**
 * 主函数
 */
fun main(args: Array<String>) {
    runApplication<LibraApplication>(*args)

    // 创建静态文件路径
    val rootPath = PathUtils.rootPath()
    val binFile = File(rootPath, "bin/dll")
    val dataFile = File(rootPath, "data")
    if (!binFile.exists()) {
        binFile.mkdirs()
    }
    if (!dataFile.exists()) {
        dataFile.mkdirs()
    }
    logger.info("重要信息⬇")
    logger.info("请将DLL文件 libCapricornus.so 存放到 $binFile 下")
    logger.info("请将语料文件 polyglot-zh.txt 存放到 $dataFile 下")
    logger.info("重要信息⬆")
    // 试卷存放地方
    val pageFile = File(rootPath,"\\page\\code")
    if (!pageFile.exists()){
        pageFile.mkdirs()
    }
}
