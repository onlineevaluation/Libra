package com.nuc.libra.vo

/**
 * @author 杨晓辉 2018/2/4 14:34
 * 题的类型：
 */
class PageVO {

    /**
     * 单选题
     */
    var signChoice: ArrayList<TitleVO> = ArrayList()

    /**
     * 程序题
     */
    var codeQuestion: ArrayList<TitleVO> = ArrayList()
    /**
     * 填空题
     */
    var blank: ArrayList<TitleVO> = ArrayList()

    /**
     * 简答题
     */
    var ansQuestion: ArrayList<TitleVO> = ArrayList()
    /**
     * 算法题
     */
    var algorithm:ArrayList<TitleVO> = ArrayList()
}