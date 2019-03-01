package com.nuc.libra.service

import com.nuc.libra.po.ClassAndPages
import com.nuc.libra.po.Title
import com.nuc.libra.vo.PageDetailsParam
import com.nuc.libra.vo.StudentScoreParam

/**
 * @author 杨晓辉 2018/2/3 15:55
 */
interface PaperService {

    /**
     * 获取该班级所有的试卷
     * @param classId 班级id
     */
    fun listClassPage(classId: Long): List<ClassAndPages>

    /**
     *
     * 通过 pageId 和 classId 获取 title list
     * @param pageId pageId
     * @param classId 班级id
     * @return titleList
     */
    fun getOnePage(classId: Long, pageId: Long): List<Title>

    /**
     * 试卷提交验证接口
     * @param studentId 学生id
     * @param pageId 试卷id
     */
    fun verifyPage(studentId: Long, pageId: Long)

    /**
     * 获取所有的试卷分数
     * @param studentId 学生id
     */
    fun listScore(studentId: Long): List<StudentScoreParam>

    /**
     * 获取单个试卷分数
     * @param pageId 试卷id
     * @param studentId 学生id
     */
    fun getPageScore(pageId: Long, studentId: Long): PageDetailsParam
}