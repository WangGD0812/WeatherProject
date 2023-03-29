package com.goat.weather.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingAdapter<T:Any, BINDING : ViewDataBinding> :
    RecyclerView.Adapter<BindingViewHolder<T, BINDING>>() {

    var itemClickListener: OnItemClickListener<T>? = null
    var itemViewTypeCreator: ItemViewTypeCreator? = null

    var data: List<T>? = null
        @SuppressLint("NotifyDataSetChanged")
        set(data) {
            field = data
            notifyDataSetChanged()
        }


    @get:LayoutRes
    abstract val layoutRes: Int

    fun getItem(position: Int): T? {
        return data?.getOrNull(position)
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewTypeCreator?.getItemViewType(position, getItem(position))
            ?: super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    interface OnItemClickListener<T> {
        fun onItemClick(t: T?, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T, BINDING> {
        val layout = itemViewTypeCreator?.getItemLayout(viewType) ?: layoutRes
        val binding = DataBindingUtil.inflate<BINDING>(LayoutInflater.from(parent.context), layout, parent, false)
        val holder = BindingViewHolder<T, BINDING>(binding)
        bindClick(holder, binding)
        return holder
    }

    override fun onBindViewHolder(holder: BindingViewHolder<T, BINDING>, position: Int) {
        holder.bind(getItem(position))
    }

    protected fun bindClick(holder: BindingViewHolder<*, *>, binding: BINDING) {
        binding.root.setOnClickListener {
            val position = holder.layoutPosition
            itemClickListener?.onItemClick(getItem(position), position)
        }
    }

    interface ItemViewTypeCreator{
        fun getItemViewType(position: Int, item: Any?) : Int
        fun getItemLayout(viewType: Int) : Int
    }

}