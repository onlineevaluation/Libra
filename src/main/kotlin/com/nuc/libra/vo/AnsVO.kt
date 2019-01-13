package com.nuc.libra.vo

/**
 * @author 杨晓辉 2018/2/12 16:00
 */
class AnsVO {
    var pageId: Long = 0L
    var score: Double = 0.0
    var select: ArrayList<StudentAnswerSelect> = ArrayList<StudentAnswerSelect>()
    var blank:ArrayList<StudentAnswer> = ArrayList()
    var ans = ArrayList<StudentAnswer>()
}

open class StudentAnswer {
    var id: Long = 0L
    var answer: String = ""
    var score: Double = 0.0
    var standardAnswer = ""
    lateinit var title: String
}

class StudentAnswerSelect:StudentAnswer() {
    var sectionA: String = ""
    var sectionB: String = ""
    var sectionC: String = ""
    var sectionD: String = ""
}