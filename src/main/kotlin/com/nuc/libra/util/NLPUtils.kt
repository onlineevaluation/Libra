package com.nuc.libra.util

import com.hankcs.hanlp.dictionary.CustomDictionary
import com.hankcs.hanlp.mining.word2vec.DocVectorModel
import com.hankcs.hanlp.mining.word2vec.WordVectorModel


/**
 * @author 杨晓辉 2018/2/9 10:10
 */
object NLPUtils {

    private var wordVectorModel: WordVectorModel

    /**
     * 初始化参数
     */
    init {
        val docPath: String
        val os = System.getProperty("os.name")
        docPath = if (os.contains("Windows")) {
            "C:/Users/young/Desktop/polyglot-zh.txt"
        } else {
            "~/data/polyglot-zh.txt"
        }
        wordVectorModel = WordVectorModel(docPath)
    }

    /**
     * 词语相似度
     */
    fun wordSimilar(word1: String, word2: String): Double {
        val similarity = wordVectorModel.similarity(word1, word2).toDouble()
        return when {
            similarity <= 0.0 -> 0.0
            similarity >= 1.0 -> 1.0
            else -> similarity
        }

    }


    /**
     * 用 wod2Vec 相似度进行分析
     * @param doc1 语句1
     * @param doc2 语句2
     * @return 相似度
     */
    fun docSimilar(doc1: String, doc2: String): Double {
        val docVectorModel = DocVectorModel(wordVectorModel)
        val similar = docVectorModel.similarity(doc1, doc2).toDouble()
        return when {
            similar <= 0.0 -> 0.0
            similar >= 1.0 -> 1.0
            else -> similar
        }
    }

}

