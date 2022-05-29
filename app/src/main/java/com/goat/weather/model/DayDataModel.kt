package com.goat.weather.model

data class DayDataModel(
    val time: Long?,
    val summary: String?,
    val icon: String?,
    val temperatureHigh: Double?,
    val temperatureLow: Double?
)
