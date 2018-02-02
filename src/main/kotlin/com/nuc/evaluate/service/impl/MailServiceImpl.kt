package com.nuc.evaluate.service.impl

import com.nuc.evaluate.entity.EmailMessage
import freemarker.template.Template
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig

/**
 * @author 杨晓辉 2018/2/2 14:17
 */
@Service
class MailServiceImpl {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var mailSender: JavaMailSender

    @Autowired
    lateinit var freeMarkerConfig: FreeMarkerConfig

    fun sendSimpleMain(to: Array<String>, subject: String, content: String) {
        val message = SimpleMailMessage()
        message.from = "youngxhui@163.com"
        message.to = to
        message.subject = subject
        message.text = content
        try {
            mailSender.send(message)
            logger.info("邮件发送成功")
        } catch (e: Exception) {
            logger.info("邮件发送失败")
        }
    }

    fun sendInlineMail(to: Array<String>, subject: String, emailMessage: EmailMessage, templateName: String) {
        val mimeMessage = mailSender.createMimeMessage()
        val mailHelper = MimeMessageHelper(mimeMessage)

        mailHelper.setFrom("youngxhui@163.com")
        mailHelper.setTo(to)
        mailHelper.setSubject(subject)

        val model = HashMap<String, Any>()
        model["emailMessage"] = emailMessage

        val template: Template = freeMarkerConfig.configuration.getTemplate(templateName)
        val text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model)
        mailHelper.setText(text, true)
        mailSender.send(mimeMessage)
        logger.info("邮件发送成功")

    }


}