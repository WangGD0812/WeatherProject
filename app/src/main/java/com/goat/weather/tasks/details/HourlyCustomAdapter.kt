package com.goat.weather.tasks.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goat.weather.R
import com.goat.weather.model.HourDataModel
import com.goat.weather.utils.DateUtil
import com.goat.weather.utils.ResourceUtil
import com.goat.weather.utils.StringUtil

class HourlyCustomAdapter(private val mList: List<HourDataModel>):  RecyclerView.Adapter<HourlyCustomAdapter.HourViewHolder>() {

    class HourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTemperature: TextView = itemView.findViewById(R.id.tvTemperature)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val ivWeatherIcon: ImageView = itemView.findViewById(R.id.ivWeatherIcon)

        fun fill(data: HourDataModel) {
            var hourMinuteStr = DateUtil.timestampToDateStr(data.time!! * 1000, DateUtil.TIME_WITH_HOUR_MINUTES_FORMAT)
            tvTime.text = hourMinuteStr
            tvTemperature.text = data.temperature?.toString()
            if (!StringUtil.isEmpty(data.icon)) {
                var drawableResId = ResourceUtil.getMipmapResId(data.icon!!, itemView.context)
                if (drawableResId ?: 0 > 0) {
                    ivWeatherIcon.setImageResource(drawableResId!!)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        return HourViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_hourly_list, parent, false))
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.fill(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}