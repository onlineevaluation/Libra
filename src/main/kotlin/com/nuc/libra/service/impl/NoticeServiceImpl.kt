package com.nuc.libra.service.impl

import com.nuc.libra.po.SystemNotice
import com.nuc.libra.repository.NoticeRepository
import com.nuc.libra.repository.UserAndRoleRepository
import com.nuc.libra.service.NoticeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/5/19 20:38
 */
@Service
class NoticeServiceImpl : NoticeService {

    @Autowired
    private lateinit var noticeRepository: NoticeRepository

    @Autowired
    private lateinit var userAndRoleRepository: UserAndRoleRepository

    /**
     * 获取通知
     * @return List<SystemNotice>
     */
    override fun getNotice(userId: Long): List<SystemNotice> {
        val systemNoticeList: ArrayList<SystemNotice> = ArrayList()
        val roleList = userAndRoleRepository.findByUserId(userId)
        roleList.forEach {
            if (it.roleId == 2L) {
                val noticeList = noticeRepository.findByBelongId(2.toString())
                systemNoticeList.addAll(noticeList)
            }

            if (it.roleId == 3L) {
                val noticeList = noticeRepository.findByBelongId(3.toString())
                systemNoticeList.addAll(noticeList)
            }
        }
        val noticeList = noticeRepository.findByBelongId(1.toString())
        systemNoticeList.addAll(noticeList)
        return systemNoticeList.sortedByDescending { it.createTime }
    }

    override fun getAllNotice(): List<SystemNotice> {
        return noticeRepository.findAll()
    }

}