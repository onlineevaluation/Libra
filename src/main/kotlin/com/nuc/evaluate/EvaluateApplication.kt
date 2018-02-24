package com.nuc.evaluate

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * spring boot 启动入口
 */
@SpringBootApplication
class EvaluateApplication

/**
 * 主函数
 */
fun main(args: Array<String>) {
    SpringApplication.run(EvaluateApplication::class.java, *args)
}
