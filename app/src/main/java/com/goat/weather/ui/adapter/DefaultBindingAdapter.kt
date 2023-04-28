package com.goat.weather.ui.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.goat.weather.base.BaseBindingAdapter
import com.goat.weather.base.BindingViewHolder

class DefaultBindingAdapter(@param:LayoutRes @field:LayoutRes override val layoutRes: Int)
    : BaseBindingAdapter<Any, ViewDataBinding>()