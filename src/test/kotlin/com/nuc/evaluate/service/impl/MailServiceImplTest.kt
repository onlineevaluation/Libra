package com.nuc.evaluate.service.impl

import com.nuc.evaluate.entity.EmailMessage
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import java.text.SimpleDateFormat


/**
 * @author 杨晓辉 2018/2/2 14:25
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class MailServiceImplTest {

    @Autowired
    lateinit var mailServiceImpl: MailServiceImpl

    @Test
    fun testSimpleMail() {
        val toArray: Array<String> = arrayOf("youngxhui@163.com", "youngxhui@qq.com")
        mailServiceImpl.sendSimpleMain(toArray, "test", "hello")
    }

    @Test
    fun testVmMail() {
        val to = arrayOf("youngxhui@gmail.com", "youngxhui@qq.com")
        val subject = "异常报告"
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")//设置日期格式
        val exceptionEmailMessage = EmailMessage()
        exceptionEmailMessage.exception = "这是xxx异常"
        exceptionEmailMessage.time = df.format(Date())
        mailServiceImpl.sendInlineMail(to, subject, exceptionEmailMessage)
    }


}