package com.nuc.libra.service.impl

import com.nuc.libra.po.Chapter
import com.nuc.libra.po.Course
import com.nuc.libra.po.Knowledge
import com.nuc.libra.repository.ChapterRepository
import com.nuc.libra.repository.KnowledgeRepository
import com.nuc.libra.service.ChapterService
import com.nuc.libra.vo.ChapterAndKnowledgeInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/11 15:40
 */
@Service
class ChapterServiceImpl : ChapterService {

    @Autowired
    private lateinit var chapterRepository: ChapterRepository

    @Autowired
    private lateinit var knowledgeRepository: KnowledgeRepository

    /**
     * 通过 课程获取所有的章节
     * @param course Course
     * @return List<Chapter>
     */
    override fun getTopicByCourseId(course: Course): List<Chapter> {
        return chapterRepository.findByCourseId(course.id)
    }


    /**
     * 通过课程获取课程的知识点
     * @param course Course
     * @return List<ChapterAndKnowledgeInfo>
     */
    override fun getKnowledgeByChapterId(course: Course): List<ChapterAndKnowledgeInfo> {
        return chapterRepository.findByCourseId(course.id).map { chapter ->
            return@map ChapterAndKnowledgeInfo().apply {
                this.knowledges = knowledgeRepository.findByChapterId(chapter.id)
                this.course = course
            }
        }

    }
}