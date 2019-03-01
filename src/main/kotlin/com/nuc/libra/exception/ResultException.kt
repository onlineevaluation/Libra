package com.nuc.libra.exception

import java.lang.RuntimeException

/**
 * @author 杨晓辉 2018/2/1 14:10
 */
class ResultException
    (message: String, var code: Int) : RuntimeException(message)


