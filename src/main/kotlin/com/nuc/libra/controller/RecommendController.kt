package com.nuc.libra.controller

import com.nuc.libra.po.ResourceDirctory
import com.nuc.libra.result.Result
import com.nuc.libra.service.RecommendService
import com.nuc.libra.util.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/5/5 11:18
 * 推荐算法第一波
 */
@RestController
class RecommendController {

    @Autowired
    private lateinit var recommendService: RecommendService

    /**
     * 进行资源推荐，主要是视频资源
     */
    @GetMapping("/recommend/resource/{studentId}")
    fun recommendResource(@PathVariable("studentId") studentId: Long): Result {
        val resource = recommendService.getResourceByStudentId(studentId)
        if (resource.isEmpty()) {
            val list = emptyList<ResourceDirctory>()
            return ResultUtils.success(data = list, message = "暂时没有数据可进行推荐")
        }
        return ResultUtils.success(message = "推荐数据获取成功", data = resource)
    }


}