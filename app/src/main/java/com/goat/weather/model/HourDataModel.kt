package com.goat.weather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HourDataModel(
    val time: Long?,
    val summary: String?,
    val icon: String?,
    val temperature: Double?
): Parcelable
