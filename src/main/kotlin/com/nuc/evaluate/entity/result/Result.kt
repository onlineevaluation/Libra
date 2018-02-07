package com.nuc.evaluate.entity.result

import java.io.Serializable

/**
 * @author 杨晓辉 2018/2/6 11:02
 */

class Result : Serializable {
    var studentId: Long = 0L
    var pageId: Long = 0L
    var answer = ArrayList<Answer>()

    override fun toString(): String {
        return "Result(studentId=$studentId, pageId=$pageId, ans=$answer)"
    }


}

class Json : Serializable {
    var result: Result = Result()
    override fun toString(): String {
        return "Json(result=$result)"
    }


}

class Answer : Serializable {
    var id: Long = 0L
    var ans: String = ""
    override fun toString(): String {
        return "Answer(id=$id, ans='$ans')"
    }
}