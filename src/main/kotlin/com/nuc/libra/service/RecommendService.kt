package com.nuc.libra.service

import com.nuc.libra.po.ResourceDirctory
import com.nuc.libra.service.impl.RecommendServiceImpl

/**
 * @author 杨晓辉 2019/5/5 11:20
 */
interface RecommendService {


    /**
     * 通过用户来进行资源推荐
     * @param studentId Long 学生id
     */
    fun getResourceByStudentId(studentId: Long): List<ResourceDirctory>
}