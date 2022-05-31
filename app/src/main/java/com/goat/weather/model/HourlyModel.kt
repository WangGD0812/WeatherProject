package com.goat.weather.model

data class HourlyModel(
    val summary: String?,
    val icon: String?,
    val data: ArrayList<HourDataModel>?
)
