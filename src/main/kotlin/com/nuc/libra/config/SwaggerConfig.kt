package com.nuc.libra.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author 杨晓辉 2018/2/1 14:28
 *
 * 用于生成文档页面
 *
 * [swagger页面](api://localhost:8081/swagger-ui.html)
 *
 */
@Configuration
@EnableSwagger2
class SwaggerConfig {

    /**
     * swagger2 的配置
     */
    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.nuc.libra.controller"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
            .securitySchemes(
                ArrayList<SecurityScheme>(
                    Arrays.asList(
                        ApiKey(
                            "Bearer %token",
                            "Authorization",
                            "Header"
                        )
                    )
                )
            ).genericModelSubstitutes(Optional::class.java)
    }


    private fun apiInfo(): ApiInfo {
        val date = Date()
        val formattedDate = SimpleDateFormat("yyMMddHH")
        return ApiInfoBuilder()
            .title("在线评测系统")
            .description("在线教育系统在线评测模块")
            .version(formattedDate.format(date))
            .build()
    }

}
