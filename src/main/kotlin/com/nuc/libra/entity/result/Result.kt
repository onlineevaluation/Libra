package com.nuc.libra.entity.result

import java.io.Serializable

/**
 * @author 杨晓辉 2018/2/6 11:02
 */

/**
 * 获取队列中的结果？？？？
 */
class Result : Serializable {
    var studentId: Long = 0L
    var pageId: Long = 0L
    var answer = ArrayList<Answer>()

    /**
     * toString
     */
    override fun toString(): String {
        return "Result(studentId=$studentId, pageId=$pageId, ans=$answer)"
    }


}

/**
 * 前端数据 json 对应对象
 */
class Json : Serializable {
    var result: Result = Result()
    override fun toString(): String {
        return "Json(result=$result)"
    }
}

/**
 * 答案获取类
 */
class Answer : Serializable {
    var id: Long = 0L
    var ans: String = ""
    override fun toString(): String {
        return "Answer(id=$id, ans='$ans')"
    }
}