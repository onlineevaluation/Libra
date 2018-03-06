package com.nuc.evaluate.vo

/**
 * @author 杨晓辉 2018/2/12 16:00
 */
class AnsVO {
    var pageId: Long = 0L
    var score: Double = 0.0
    var ansList = ArrayList<StudentAnswer>()
}


class StudentAnswer {

    var id: Long = 0
    var answer: String = ""
    var score: Double = 0.0
    var standardAnswer = ""

}