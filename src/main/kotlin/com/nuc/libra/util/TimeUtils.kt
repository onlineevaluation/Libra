package com.nuc.libra.util

/**
 * @author 杨晓辉 2019/5/21 8:58
 */
object TimeUtils {

    private const val hoursOfMillisecond = 3_600_000

    private const val minuteOfMillisecond = 60_000

    private const val secondOfMillisecond = 1_000
    /**
     * 将毫秒格式为时分秒 格式为：h小时m分钟s秒
     * @param millisecond Long 毫秒
     * @return String
     */
    fun formatTime(millisecond: Long): String {

        // 小时
        val h = millisecond / hoursOfMillisecond
        // 分钟
        val m = (millisecond - h * hoursOfMillisecond) / minuteOfMillisecond
        // 秒
        val s = (millisecond - h * hoursOfMillisecond - m * minuteOfMillisecond) / secondOfMillisecond

        return "$h 小时 $m 分钟 $s 秒"
    }

}
