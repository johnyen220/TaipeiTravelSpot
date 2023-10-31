package com.cbes.ezreturn.utils


import java.util.*

/**
 * Created by John Yen on 2022/6/23
 * Return Helper Technology Co., Ltd. (c) 2022 . All rights reserved.
 */
object ParseSpeed {
    private const val B: Long = 1
    private const val KB = B * 1024
    private const val MB = KB * 1024
    private const val GB = MB * 1024
    fun parseSpeed(bytes: Double, inBits: Boolean): String {
        val value = if (inBits) bytes * 8 else bytes
        return if (value < KB) {
            String.format(
                Locale.getDefault(),
                "%.1f " + (if (inBits) "b" else "B") + "/s",
                value
            )
        } else if (value < MB) {
            String.format(
                Locale.getDefault(),
                "%.1f K" + (if (inBits) "b" else "B") + "/s",
                value / KB
            )
        } else if (value < GB) {
            String.format(
                Locale.getDefault(),
                "%.1f M" + (if (inBits) "b" else "B") + "/s",
                value / MB
            )
        } else {
            String.format(
                Locale.getDefault(),
                "%.2f G" + (if (inBits) "b" else "B") + "/s",
                value / GB
            )
        }
    }
}