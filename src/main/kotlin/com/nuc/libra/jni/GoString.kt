package com.nuc.libra.jni

import com.sun.jna.Structure

/**
 * @author 杨晓辉 2019-02-11 10:23
 */
open class GoString(str: String) : Structure() {

    lateinit var str: String

    var length: Long = 0L

    override fun getFieldOrder(): MutableList<String> {
        val files = ArrayList<String>()
        files.add("str")
        files.add("length")
        return files
    }

    class ByValue(str: String) : GoString(str), Structure.ByValue

    class ByReference(str: String) : GoString(str), Structure.ByReference
}