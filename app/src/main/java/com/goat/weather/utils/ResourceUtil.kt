package com.goat.weather.utils

import android.content.Context


object ResourceUtil {

    private const val RESOURCE_TYPE_DRAWABLE = "mipmap"

    fun getMipmapResId(imgName: String, context: Context): Int? {
        var resource = context.resources
        return resource.getIdentifier(imgName, RESOURCE_TYPE_DRAWABLE, context.packageName)
    }

}