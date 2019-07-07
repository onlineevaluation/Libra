package com.nuc.libra.service.impl

import com.nuc.libra.po.ResourceDirctory
import com.nuc.libra.repository.*
import com.nuc.libra.service.RecommendService
import com.nuc.libra.vo.KnowledgeAndSize
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import kotlin.streams.toList

/**
 * @author 杨晓辉 2019/5/5 11:20
 */
@Service
class RecommendServiceImpl : RecommendService {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var answerRepository: StudentAnswerRepository

    @Autowired
    private lateinit var titleRepository: TitleRepository

    @Autowired
    private lateinit var resourceDirctoryRepository: ResourceDirctoryRepository

    @Autowired
    private lateinit var commendRepository: CommentRepository

    @Autowired
    private lateinit var resourceClassRepository: ResourceClassRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    /**
     * 获得资源推荐列表
     * @param studentId Long
     * @return List<ResourceDirctory>
     */
    override fun getResourceByStudentId(studentId: Long): List<ResourceDirctory> {
        val classId = studentRepository.getOne(studentId).classId
        val answerByStudentIds = answerRepository.findByStudentId(studentId)
        val knowledegeAndsizeMap = answerByStudentIds.subList(
            0,
            if (answerByStudentIds.size >= 50) {
                50
            } else {
                answerByStudentIds.size
            }
        ).map { studentAnswer ->
            titleRepository.getOne(studentAnswer.titleId)
        }.groupBy {
            it.knowledgeId
        }

        val knowledgeList = knowledegeAndsizeMap.map {
            KnowledgeAndSize().apply {
                this.knowledgeId = it.key
                this.size = it.value.size
            }
        }
        val videoType = 0L
        // 现在时间
        val weekTime = 604_800_000 //1000 * 60 * 60 * 24 * 7
        val nowTime = System.currentTimeMillis()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val videoRankAllList = ArrayList<VideoRank>()
        // 推荐资源信息 排名 = 相关评论（热度）* 0.4 + 播放量 * 0.6 * 发布时间(一周以内)(进行提权 * 1.2)
        knowledgeList.forEach { knowledgeAndSize ->

            val resourceList =
                resourceDirctoryRepository.findByKnowledgeIdAndType(
                    knowledgeAndSize.knowledgeId,
                    type = videoType
                )

            val videoRankList = resourceList.map {

                var weight = 1.0f
                if (it.addtime >= (nowTime - weekTime)) {
                    weight = 1.2f
                }

                // 获取该视频的评论数
                val commentCount = commendRepository.countByVideoId(it.id)

                // 计算排名
                val rankScore: Float = commentCount * 0.4f + it.playTimes * 0.6f * weight
//                val resourceClass = resourceClassRepository.findByResourceIdAndClassId(it.id, classId)
                VideoRank().apply {
                    this.rankScore = rankScore
                    this.videoId = it.id
//                    this.startTime = resourceClass.startTime
//                    this.endTime = resourceClass.endTime
                }

            }.toList()
            videoRankAllList.addAll(videoRankList)
        }
        videoRankAllList.sortedBy { it.rankScore }

        videoRankAllList.map {
            resourceDirctoryRepository.getOne(it.videoId)
        }
        val list = videoRankAllList.parallelStream().map {
            resourceDirctoryRepository.getOne(it.videoId)
        }.toList()

        return list
    }

    /**
     * 封装查询结果的中间类
     * @property videoId Long
     * @property rankScore Float
     */
    class VideoRank {

        var videoId: Long = 0L
        var rankScore = 0.0f
        var startTime: Long = 0L
        var endTime: Long = 0L
        override fun toString(): String {
            return "VideoRank(videoId=$videoId, rankScore=$rankScore)"
        }
    }
}
