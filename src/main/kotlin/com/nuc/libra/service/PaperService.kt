package com.nuc.libra.service

import com.nuc.libra.po.ClassAndPages
import com.nuc.libra.po.Title
import com.nuc.libra.vo.*
import java.awt.print.Paper

/**
 * @author 杨晓辉 2018/2/3 15:55
 */
interface PaperService {

    /**
     * 获取该班级所有的试卷
     * @param classId 班级id
     */
    fun listClassPage(classId: Long): List<PageAndClassInfo>

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

    /**
     * 人工组卷
     * @param courseId Long
     * @param typeIds IntArray
     */
    fun artificial(artificialPaperParam: ArtificialPaperParam): PageInfo

    /**
     *
     * @param courseId Long
     * @param typeIds IntArray
     */
    fun ai(courseId: Long, typeIds: IntArray)

    /**
     * 获取对应试题
     * @param courseId Long
     * @param typeIds IntArray
     * @return Map<String, List<Title>>
     */
    fun getTitles(courseId: Long, typeIds: IntArray, chapterIds: IntArray): List<List<Title>>

    /**
     * 获取所有的试卷
     * @return List<PageInfo>
     */
    fun getAllPapers(): List<PageInfo>


    /**
     * 获取单张试卷信息
     * @param paperId Long
     * @return PageInfo
     */
    fun getOnePaper(paperId: Long): PageInfo


    /**
     * 保存班级关系试卷
     * @param pageClassParam PageClassParam
     */
    fun savePageAndClass(pageClassParam: PageClassParam)

    fun getCreateName(pageId: Long): String
}