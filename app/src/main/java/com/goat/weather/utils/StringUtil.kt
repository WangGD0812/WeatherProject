package com.goat.weather.utils


object StringUtil {
    const val COMMA = ","
    const val EMPTY = ""
    const val FORWARD_SLASH = " / "
    const val TEMPERATURE_UNIT_C = "°C"

    fun isEmpty(str: String?): Boolean {
        return str == null || str.isEmpty()
    }
}