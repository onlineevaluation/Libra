package com.nuc.libra.util

import org.junit.Test

/**
 * @author 杨晓辉 2019-02-03 21:27
 */
class NLPUtilsTest {

    private val docA = "奥运会女排夺冠"
    private val docB = "世界锦标赛胜出"
    private val docC = "农民在江苏种水稻"


    @Test
    fun docVectorModelTest() {
        val similar = NLPUtils.docSimilar(docA, docB)
        println(similar)
    }

    @Test
    fun docVectorModelFailTest() {
        val similar = NLPUtils.docSimilar(docA, docC)
        println(similar)
    }
}