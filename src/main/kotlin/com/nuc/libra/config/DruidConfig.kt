package com.nuc.libra.config


import com.alibaba.druid.pool.DruidDataSource
import com.alibaba.druid.support.http.StatViewServlet
import com.alibaba.druid.support.http.WebStatFilter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import javax.servlet.Filter
import javax.sql.DataSource

/**
 * @author 杨晓辉 2019-01-20 19:14
 * Druid 数据库连接池配置
 */
@Configuration
class DruidConfig {


    /**
     * 注入连接池配置
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    fun druid(): DataSource {
        return DruidDataSource()
    }

    /**
     * 启动监控
     */
    @Bean
    fun statViewServlet(): ServletRegistrationBean<StatViewServlet> {
        val bean = ServletRegistrationBean(StatViewServlet(), "/druid/*")
        val initParams = HashMap<String, String>()
        initParams["loginUsername"] = "admin"
        initParams["loginPassword"] = "admin"
        initParams["allow"] = "localhost"
        initParams["deny"] = ""
        bean.initParameters = initParams
        return bean
    }

    /**
     * 拦截请求进行监控
     */
    @Bean
    fun webStatFilter(): FilterRegistrationBean<Filter> {
        val bean = FilterRegistrationBean<Filter>()
        bean.filter = WebStatFilter()
        val initParams = HashMap<String, String>()
        initParams["exclusions"] = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"
        bean.initParameters = initParams
        bean.urlPatterns = Arrays.asList("/*")
        return bean
    }

}