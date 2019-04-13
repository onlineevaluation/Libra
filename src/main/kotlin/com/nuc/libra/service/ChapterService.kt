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

    /**
     * 通过课程获取章节
     * @param course Course 课程
     * @return List<Chapter> 知识点
     */
    fun getTopicByCourseId(course: Course): List<Chapter>


    /**
     * 通过课程获取章节和知识点
     * @param course Course 课程
     * @return List<ChapterAndKnowledgeInfo> 章节和知识点的对应关系
     */
    fun getKnowledgeByChapterId(course: Course): List<ChapterAndKnowledgeInfo>
}