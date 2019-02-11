package com.nuc.libra.util

import com.hankcs.hanlp.mining.word2vec.DocVectorModel
import com.hankcs.hanlp.mining.word2vec.WordVectorModel


/**
 * @author 杨晓辉 2018/2/9 10:10
 */
object NLPUtils {
    private var docVectorModel: DocVectorModel

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
        docVectorModel = DocVectorModel(WordVectorModel(docPath))
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

/**
 * 该方法存在问题 应该使用 top-k 进行检查
 */
fun main(args: Array<String>) {
    val s1 = "JVM哈哈哈哈"
    val s2 = "java虚拟机嘻嘻嘻嘻嘻"

    val similar = NLPUtils.docSimilar(s1, s2)
    println("similar is $similar")
}

