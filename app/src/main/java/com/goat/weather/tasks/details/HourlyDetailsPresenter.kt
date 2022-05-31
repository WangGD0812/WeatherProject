package com.goat.weather.tasks.details

import com.goat.weather.model.HourDataModel
import com.goat.weather.tasks.Constants
import com.goat.weather.utils.DateUtil
import javax.inject.Inject

class HourlyDetailsPresenter@Inject constructor(): HourlyDetailsContract.Presenter() {

    override fun processHourlyData(list: List<HourDataModel>?) {
        if (list != null && list.isNotEmpty()) {
            var currentHour = DateUtil.timestampToDateStr(System.currentTimeMillis(), DateUtil.TIME_WITH_HOUR_FORMAT).toIntOrNull()
            if (null != currentHour) {
                var visibleHourList = mutableListOf<HourDataModel>()
                for (hourData in list) {
                    hourData.time?.let {
                        var hour = DateUtil.timestampToDateStr(it * 1000, DateUtil.TIME_WITH_HOUR_FORMAT).toIntOrNull()
                        if (hour?: -1 >= currentHour) {
                            visibleHourList.add(hourData)
                        }
                    }
                }
                if (visibleHourList.size > 0) {
                    mIView?.loadHourlyData(visibleHourList)
                } else {
                    mIView?.loadHourlyDataFailed(Constants.DISPLAY_MSG_DATA_EXCEPTION)
                }
            }
        }
    }

}