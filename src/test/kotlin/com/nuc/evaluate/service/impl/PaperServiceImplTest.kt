package com.nuc.evaluate.service.impl

import org.junit.Assert.*
import org.junit.Test

/**
 * @author 杨晓辉 2018/2/10 11:10
 */

class PaperServiceImplTest {
    @Test
    fun splitWord() {

        var word = "【xx】【aa】【bb】"
        val list = word.split("】".toRegex())
        list.map {
            println(it.substringAfter('【'))
        }


    }
}