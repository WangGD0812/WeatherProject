package com.goat.weather.net

import androidx.collection.ArrayMap

object ApiHeader {

    private const val KEY_CONTENTTYPE: String = "Content-Type"
    private const val APPLICATION_JSON_UTF8: String = "application/json; charset=utf-8"

    fun getCommonHeader(): ArrayMap<String, String> {
        val headersMap = ArrayMap<String, String>()
        headersMap[KEY_CONTENTTYPE] = APPLICATION_JSON_UTF8
        return headersMap
    }

}