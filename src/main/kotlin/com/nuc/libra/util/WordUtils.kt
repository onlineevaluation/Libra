package com.nuc.libra.util

import com.hankcs.hanlp.HanLP
import com.hankcs.hanlp.mining.word2vec.DocVectorModel
import com.hankcs.hanlp.mining.word2vec.Word2VecTrainer
import com.hankcs.hanlp.mining.word2vec.WordVectorModel
import org.apdplat.word.WordSegmenter
import org.apdplat.word.analysis.CosineTextSimilarity
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer


/**
 * @author 杨晓辉 2018/2/9 10:10
 */
object WordUtils {
    private var docVectorModel: DocVectorModel

    /**
     * 初始化参数
     */
    init {
        val docPath = "C:\\Users\\young\\Desktop\\polyglot-zh\\polyglot-zh.txt"
        docVectorModel = DocVectorModel(WordVectorModel(docPath))
    }

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
        // 采用新的分词方式

        val cosSimilarity = CosineTextSimilarity()
        return cosSimilarity.similarScore(answer, studentAns)
    }


    /**
     * 用 wod2Vec 相似度进行分析
     * @param docA 语句1
     * @param docB 语句2
     * @return 相似度
     */
    fun docSimilar(docA: String, docB: String): Double {
        return docVectorModel.similarity(docA, docB).toDouble()
    }

}


