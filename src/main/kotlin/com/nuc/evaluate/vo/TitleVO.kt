package com.nuc.evaluate.vo

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
    var score: Long = 0
    var completeTime: Long = 0
    var sectionA: String? = null
    var sectionB: String? = null
    var sectionC: String? = null
    var sectionD: String? = null
    private var sectionList: List<String> = ArrayList()


    fun setSection(category: String) {
        when (category) {
        // 单选题和多选题
            "0", "1" -> {
                sectionList = title.split("\n")
                sectionA = sectionList[1].substring(1).trim()
                sectionB = sectionList[2].substring(1).trim()
                sectionC = sectionList[3].substring(1).trim()
                sectionD = sectionList[4].substring(1).trim()
                title = sectionList[0].trim()
            }

        // 填空题
            "3" -> {

            }
            else -> {
            }
        }
    }
}