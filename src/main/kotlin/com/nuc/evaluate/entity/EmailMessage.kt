package com.nuc.evaluate.entity

import java.io.Serializable


/**
 * @author 杨晓辉 2018/2/2 15:15
 */
class EmailMessage : Serializable {


    lateinit var exception: String

    lateinit var time: String

    override fun toString(): String {
        return "EmailMessage(exception='$exception', time='$time')"
    }

}