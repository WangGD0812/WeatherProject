package com.goat.weather.model

enum class Blocks(var block: String) {
    CURRENTLY("currently"),
    MINUTELY("minutely"),
    HOURLY("hourly"),
    DAILY("daily"),
    ALERTS("alerts"),
    FLAGS("flags")
}