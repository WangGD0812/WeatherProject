package com.goat.weather.model

data class HourDataModel(
    val time: Long?,
    val summary: String?,
    val icon: String?,
    val temperature: Double?
)
