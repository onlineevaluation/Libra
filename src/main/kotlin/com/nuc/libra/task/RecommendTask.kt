package com.nuc.libra.task

import com.nuc.libra.po.StudentAnswer
import com.nuc.libra.repository.StudentAnswerRepository
import com.nuc.libra.repository.TitleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author 杨晓辉 5/3/2019 6:49 PM
 */
@Component
class RecommendTask {

    @Autowired
    private lateinit var answerRepository: StudentAnswerRepository

    @Autowired
    private lateinit var titleRepository: TitleRepository

    fun recommendResource() {
        var list = mutableListOf<RecommendKnowledge>()
        val studentAnswerGroup = answerRepository.findAll().groupBy { it.studentId }
        studentAnswerGroup.map { entry: Map.Entry<Long, List<StudentAnswer>> ->
            val map = entry.value.filter { it.score == 0.0 }.groupBy {
                titleRepository.getOne(it.titleId).knowledgeId
            }

            list = map.map {
                RecommendKnowledge().apply {
                    this.knowledgeId = it.key
                    this.size = it.value.size
                }
            }.toMutableList()
        }

        list.forEach { it ->
            println("it $it")
        }

    }

}


class RecommendKnowledge {

    var knowledgeId: Long = 0
    var size: Int = 0

}