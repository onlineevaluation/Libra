package com.nuc.libra.service

import com.nuc.libra.po.Chapter
import com.nuc.libra.po.Course
import com.nuc.libra.vo.ChapterAndKnowledgeInfo
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/11 15:38
 */
@Service
interface ChapterService {

    fun getTopicByCourseId(course: Course): List<Chapter>


    fun getKnowledgeByChapterId(course: Course): List<ChapterAndKnowledgeInfo>
}