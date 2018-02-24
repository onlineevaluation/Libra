package com.nuc.evaluate.service.impl

import com.nuc.evaluate.entity.EmailMessage
import com.nuc.evaluate.service.MailService
import freemarker.template.Template
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig

/**
 * @author 杨晓辉 2018/2/2 14:17
 */
@Service
class MailServiceImpl : MailService {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var mailSender: JavaMailSender

    @Autowired
    lateinit var freeMarkerConfig: FreeMarkerConfig


    /**
     * 邮件发送服务
     * @param to 发送给谁
     * @param subject 发送主题
     * @param emailMessage 发送信息
     * @param templateName 使用模板名称
     */
    override fun sendInlineMail(to: Array<String>, subject: String, emailMessage: EmailMessage, templateName: String) {
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