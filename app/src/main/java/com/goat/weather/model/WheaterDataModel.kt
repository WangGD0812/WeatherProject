package com.goat.weather.model

data class WheaterDataModel(
    val latitude: Double?,
    val longitude: Double?,
    val timezone: String?,
    val hourly: HourDataModel?,
    val daily: DailyModel?,
    val offset: Int?
)
