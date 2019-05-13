package com.nuc.libra.service

import com.nuc.libra.vo.ClassScoreInfo
import com.nuc.libra.vo.TitleErrorInfo
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/5/9 9:26
 */

interface ExamService {

    /**
     * 获取所有的课程总数
     * @param studentId Long
     * @return Long
     */
    fun getClassExamPageCount(studentId: Long): Int


    /**
     * 获取学生所有测错题
     */
    fun getStudentAllErrorTitleKnowledge(classId: Long, pageId: Long): ClassScoreInfo

    /**
     * 获取学生错误详细
     * @param classId Long
     * @param pageId Long
     * @return TitleErrorInfo
     */
    fun getStudentErrorInfo(classId: Long, pageId: Long): TitleErrorInfo
}