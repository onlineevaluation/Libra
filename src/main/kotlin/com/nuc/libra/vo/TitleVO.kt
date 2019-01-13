package com.nuc.libra.vo

import com.nuc.libra.po.Title

/**
 * @author 杨晓辉 2018/2/4 10:26
 * 返回到页面的试题视图
 */
class TitleVO {
    var id: Long = 0
    lateinit var num: String
    lateinit var title: String
    lateinit var category: String
    var difficulty: String? = null
    var score: Double = 0.0
    var completeTime: Long = 0
    var sectionA: String? = null
    var sectionB: String? = null
    var sectionC: String? = null
    var sectionD: String? = null
    var blankNum: Int = 0


    fun setSection(category: String, title: Title) {
        when (category) {
        // 单选题
            "1" -> {
                sectionA = title.sectiona
                sectionB = title.sectionb
                sectionC = title.sectionc
                sectionD = title.sectiond
            }

        // 填空题
            "3" -> {
                val sb = StringBuilder()
                val titleList = this.title.split("_{1,10}_".toRegex())
                for (i in 0 until titleList.size - 1) {
                    sb.append(titleList[i])
                    sb.append("【 】")
                }
                sb.append(titleList.last())
                this.title = sb.toString().trim()
                blankNum = titleList.size - 1
            }
            else -> {
            }
        }
    }

    override fun toString(): String {
        return "TitleVO(id=$id, num='$num', title='$title', category='$category', difficulty=$difficulty, score=$score, completeTime=$completeTime, sectionA=$sectionA, sectionB=$sectionB, sectionC=$sectionC, sectionD=$sectionD, blankNum=$blankNum)"
    }


}