package com.nuc.evaluate.util

import org.apdplat.word.WordSegmenter
import org.apdplat.word.analysis.CosineTextSimilarity

/**
 * @author 杨晓辉 2018/2/9 10:10
 */
object WordUtils {

    /**
     * 简答题判断方法
     */
    fun ansCheck(studentAns: String, answer: String): Double {
        val studentAnsList = WordSegmenter.seg(studentAns)
        val answerList = WordSegmenter.seg(answer)

        val cosSimilarity = CosineTextSimilarity()
        return cosSimilarity.similarScore(answerList, studentAnsList)
    }

    /**
     * 填空题判断方法
     */
    fun blankCheck(studentAns: String, answer: String): Double {
        val cosSimilarity = CosineTextSimilarity()
        return cosSimilarity.similarScore(answer, studentAns)
    }

}