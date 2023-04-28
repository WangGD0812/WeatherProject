package com.goat.weather.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


/**
 * The base class for all activity, activity public property or function.
 * For example: freeing common resource, page event track, common style,etc.
 */
open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}