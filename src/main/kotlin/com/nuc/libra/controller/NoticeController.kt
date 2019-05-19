package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.NoticeService
import com.nuc.libra.util.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/5/19 20:33
 */
@RestController
class NoticeController {

    @Autowired
    private lateinit var noticeService: NoticeService

    /**
     * 添加通知
     */
    @PostMapping("/notice")
    fun addNotice() {

    }


    /**
     * 根据用户角色获取通知
     * @param userId ") userId: Long
     * @return Result
     */
    @GetMapping("/notice/{userId}")
    fun getNotice(@PathVariable("userId") userId: Long): Result {
        val noticeList = noticeService.getNotice(userId)
        return ResultUtils.success(data = noticeList)
    }

    @GetMapping("/notices")
    fun getAllNotice() {

    }

}