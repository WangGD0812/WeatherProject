package com.goat.weather.ui.activity

import android.os.Bundle
import com.goat.weather.R
import com.goat.weather.base.BaseActivity


class MainActivity: BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}