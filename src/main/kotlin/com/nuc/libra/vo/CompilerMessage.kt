package com.nuc.libra.vo

/**
 * @author 杨晓辉 2018-03-10 20:28
 */
class CompilerMessage {
    //    lateinit var message: String
    var code: String? = null
    var kind: String? = null
    var startPosition: Long? = null
    var endPosition: Long? = null
    var position: Long? = null
    var source: Any? = null
    var message: String? = null
    var columnNumber: Long? = null
    var lineNumber: Long? = null

    override fun toString(): String {
        return "CompilerMessage(code=$code, startPosition=$startPosition, endPosition=$endPosition, position=$position, source=$source, Message=$message, columnNumber=$columnNumber, lineNumber=$lineNumber)"
    }


}