package com.nuc.libra

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * spring boot 启动入口
 */
@SpringBootApplication
class LibraApplication

/**
 * 主函数
 */
fun main(args: Array<String>) {
    runApplication<LibraApplication>(*args)
}
