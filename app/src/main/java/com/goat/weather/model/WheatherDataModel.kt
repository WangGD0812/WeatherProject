package com.goat.weather.model

data class WheatherDataModel(
    val latitude: Double?,
    val longitude: Double?,
    val timezone: String?,
    val hourly: HourlyModel?,
    val daily: DailyModel?,
    val offset: Int?
)
