package com.goat.weather.utils

import android.content.Context


object ResourceUtil {

    private const val RESOURCE_TYPE_DRAWABLE = "mipmap"

    fun getMipmapResId(imgName: String, context: Context): Int? {
        var name = if (imgName.contains("-")) imgName.replace("-", "_") else imgName
        return context.resources.getIdentifier("ic_$name", RESOURCE_TYPE_DRAWABLE, context.packageName)
    }

}