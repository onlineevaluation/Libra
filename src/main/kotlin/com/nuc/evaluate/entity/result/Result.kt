package com.nuc.evaluate.entity.result

import com.alibaba.fastjson.annotation.JSONField
import java.io.Serializable

/**
 * @author 杨晓辉 2018/2/6 11:02
 */

class Result : Serializable {
    var studentId: Long = 0L
    var pageId: Long = 0L
    var signChoice: ArrayList<Answer> = ArrayList()
    var multipleChoice: ArrayList<Answer> = ArrayList()
    var blank: ArrayList<Answer> = ArrayList()
    var trueOrFalse: ArrayList<Answer> = ArrayList()
    @JSONField(name = "code")
    var codeQuestion: ArrayList<Answer> = ArrayList()
    @JSONField(name = "draw")
    var drawingQuestion: ArrayList<Answer> = ArrayList()
    @JSONField(name = "ans")
    var ansQuestion: ArrayList<Answer> = ArrayList()

    override fun toString(): String {
        return "Result(studentId=$studentId, pagesId=$pageId, signChoice=$signChoice, " +
                "multipleChoice=$multipleChoice, blank=$blank, trueOrFalse=$trueOrFalse, " +
                "codeQuestion=$codeQuestion, drawingQuestion=$drawingQuestion, ansQuestion=$ansQuestion)"
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
    var answer: String = ""
    override fun toString(): String {
        return "Answer(id=$id, ans='$answer')"
    }
}