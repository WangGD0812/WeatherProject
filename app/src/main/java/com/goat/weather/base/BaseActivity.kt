package com.goat.weather.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * The base class for all activity, activity public property or function.
 * For example,page event track, common style.
 */
abstract class BaseActivity: AppCompatActivity() {

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }

}