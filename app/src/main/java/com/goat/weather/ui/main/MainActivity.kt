package com.goat.weather.ui.main

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.goat.weather.R
import com.goat.weather.base.BaseActivity
import com.goat.weather.base.BaseBindingAdapter
import com.goat.weather.databinding.ActivityMainBinding
import com.goat.weather.domain.MainViewModel
import com.goat.weather.ui.adapter.DefaultBindingAdapter


class MainActivity: BaseActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainBinding.apply {
            lifecycleOwner = this@MainActivity
        }
    }
}