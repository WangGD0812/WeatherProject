package com.goat.weather.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.goat.weather.BR


class BindingViewHolder<T, BINDING : ViewDataBinding>(val binding: BINDING)
    : RecyclerView.ViewHolder(binding.root){

    fun bind(t: T?) {
        binding.setVariable(BR.item, t)
    }

}