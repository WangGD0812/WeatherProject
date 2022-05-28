package com.goat.weather.ui.activity

import android.os.Bundle
import com.goat.weather.R
import com.goat.weather.base.BaseActivity


class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initPageLayoutID(): Int {
        return R.layout.activity_main
    }


}