package com.nuc.libra

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * spring boot 启动入口
 */
@SpringBootApplication
class EvaluateApplication

/**
 * 主函数
 */
fun main(args: Array<String>) {
    runApplication<EvaluateApplication>(*args)
}
