package com.nuc.libra.vo


/**
 * @author 杨晓辉 2018/2/4 10:26
 * 返回到页面的试题视图
 */
class TitleVO {

    var id: Long = 0
    var title: String = ""
    var category: String = ""
    var difficulty: String? = null
    var score: Double = 0.0
    var completeTime: Long = 0
    var sectionA: String? = null
    var sectionB: String? = null
    var sectionC: String? = null
    var sectionD: String? = null
    var blankNum: Int = 0


    override fun toString(): String {
        return "TitleVO(id=$id,  title='$title', category='$category', difficulty=$difficulty, score=$score, completeTime=$completeTime, sectionA=$sectionA, sectionB=$sectionB, sectionC=$sectionC, sectionD=$sectionD, blankNum=$blankNum)"
    }

}