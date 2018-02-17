package com.nuc.evaluate.service

import com.nuc.evaluate.entity.result.Json
import com.nuc.evaluate.po.ClassAndPages
import com.nuc.evaluate.po.StudentScore
import com.nuc.evaluate.po.Title
import com.nuc.evaluate.vo.AnsVO

/**
 * @author 杨晓辉 2018/2/3 15:55
 */
interface PaperService {
    fun listClassPage(classId: Long): List<ClassAndPages>

    fun getOnePage(pageId: Long, classId: Long): List<Title>

    fun verifyPage(result: Json)

    fun listScore(studentId: Long): List<StudentScore>

    fun getPageScore(pageId: Long, studentId: Long): AnsVO
}