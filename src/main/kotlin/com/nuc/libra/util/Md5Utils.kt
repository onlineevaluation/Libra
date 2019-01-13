package com.nuc.libra.util

import java.security.MessageDigest


/**
 * @author 杨晓辉 2018/2/2 17:51
 * md5 加密工具类用于加密密码
 * 默认没有加盐
 */
object Md5Utils {

    private const val salt: Int = 0

    /**
     * MD5加密
     * @param message 要加密信息
     */
    fun md5(message: String): String {
        val sb = StringBuilder()
        val messageDigest = MessageDigest.getInstance("MD5")
        val digest: ByteArray = messageDigest.digest(message.toByteArray())
        for (i in 0 until digest.size) {
            val result: Int = digest[i].toInt() and (0xff)
            val hexString = Integer.toHexString(result)
            if (hexString.length < 2) {
                sb.append("0")
            }
            sb.append(hexString)
        }
        return sb.toString()
    }
}
