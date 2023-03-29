package com.goat.weather.base

import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.goat.weather.ui.adapter.DefaultBindingAdapter


@BindingAdapter(value = ["data", "itemLayout", "itemClick","itemViewType"], requireAll = false)
fun setData(
    recyclerView: RecyclerView,
    data: List<Any>?,
    @LayoutRes itemLayout: Int,
    listener: BaseBindingAdapter.OnItemClickListener<Any>?,
    itemViewTypeCreator: BaseBindingAdapter.ItemViewTypeCreator?
) {
    val adapter = recyclerView.adapter
    if (adapter == null) {
        val simpleBindingAdapter = DefaultBindingAdapter(itemLayout)
        simpleBindingAdapter.data = data
        simpleBindingAdapter.itemClickListener = listener
        simpleBindingAdapter.itemViewTypeCreator = itemViewTypeCreator
        recyclerView.adapter = simpleBindingAdapter
    } else if (adapter is BaseBindingAdapter<*, *>) {
        (adapter as BaseBindingAdapter<Any, ViewDataBinding>).data = data
        adapter.itemViewTypeCreator = itemViewTypeCreator
    }
}