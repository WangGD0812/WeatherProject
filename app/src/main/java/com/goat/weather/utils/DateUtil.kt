package com.goat.weather.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    const val TIME_WITH_FORMAT = "yyyy-MM-dd"
    const val TIME_WITH_HOUR_FORMAT = "HH"
    const val TIME_WITH_HOUR_MINUTES_FORMAT = "HH:mm"

    /**
     * Timestamp to date
     */
    fun timestampToDateStr(time: Long, format: String): String {
        var simpleDateFormat = SimpleDateFormat(format)
        val date = Date(time)
        return simpleDateFormat.format(date)
    }

    fun getTimestampSeconds(): Long {
        return System.currentTimeMillis() / 1000
    }
}