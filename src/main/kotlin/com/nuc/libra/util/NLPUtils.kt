package com.nuc.libra.util

import com.hankcs.hanlp.corpus.tag.Nature
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary
import com.hankcs.hanlp.mining.word2vec.DocVectorModel
import com.hankcs.hanlp.mining.word2vec.WordVectorModel
import com.hankcs.hanlp.seg.common.Term
import com.hankcs.hanlp.tokenizer.StandardTokenizer
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList


/**
 * @author 杨晓辉 2018/2/9 10:10
 */
object NLPUtils {

    private var wordVectorModel: WordVectorModel

    private var stopWords: ArrayList<String> = ArrayList()
    private val rootPath = PathUtils.rootPath()

    /**
     * 初始化参数
     */
    init {
        val docPath = "$rootPath/data/polyglot-zh.txt"
        wordVectorModel = WordVectorModel(docPath)
        initStopWord()
    }

    /**
     * 初始化停词
     */
    private fun initStopWord() {
        val stream = Files.newInputStream(Paths.get("$rootPath/data/stopword.txt"))
        stream.buffered().reader().use { line ->
            stopWords.addAll((line.readLines()))
        }
        stopWords.add(" ")
        stopWords.add("  ")
        stopWords.add("   ")
        stopWords.add("    ")
        stopWords.add("     ")
    }

    /**
     * 去停词
     * @param termList MutableList<Term>
     * @return MutableList<String>
     */
    private fun removeStopWords(termList: MutableList<Term>): MutableList<String> {
        val wordList = termList.map { term ->
            term.word
        }.toMutableList()
        wordList.removeAll(stopWords)
        return wordList
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

    /**
     * 词频统计
     * @param str String
     * @param limitFrequency 最低词频限制，低于该词频的字符不会统计，默认为 2
     * @return TreeMap<String, Int>
     */
    fun wordFrequency(str: String, limitFrequency: Int = 2): Map<String, Int> {
        val wordMap = TreeMap<String, Int>()
        val segment = StandardTokenizer.segment(str)
        val termList = removeStopWords(segment)
        termList.forEach { word ->
            wordMap[word] = wordMap.getOrDefault(word, 0) + 1
        }

        return wordMap.filter { it.value >= limitFrequency }
    }

}