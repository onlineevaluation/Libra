package com.nuc.evaluate.service

import com.nuc.evaluate.entity.EmailMessage

/**
 * @author 杨晓辉 2018/2/3 9:28
 */
interface MailService {

    /**
     * 邮件发送服务
     * @param to 发送给谁
     * @param subject 发送主题
     * @param emailMessage 发送信息
     * @param templateName 使用模板名称
     */
    fun sendInlineMail(to: Array<String>, subject: String, emailMessage: EmailMessage, templateName: String)
}