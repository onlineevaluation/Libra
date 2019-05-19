package com.nuc.libra.service

import com.nuc.libra.po.SystemNotice

/**
 * @author 杨晓辉 2019/5/19 20:38
 */
interface NoticeService {

    /**
     * 获取通知
     * @return List<SystemNotice>
     */
    fun getNotice(userId: Long): List<SystemNotice>

    /**
     * 获取所有的系统通知
     * @return List<SystemNotice>
     */
    fun getAllNotice(): List<SystemNotice>

}