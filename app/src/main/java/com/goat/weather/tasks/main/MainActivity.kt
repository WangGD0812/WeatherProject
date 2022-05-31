package com.goat.weather.tasks.main

import android.os.Bundle
import android.widget.Toast
import com.goat.weather.R
import com.goat.weather.base.BaseActivity
import com.goat.weather.model.DayDataModel
import com.goat.weather.utils.DateUtil
import com.goat.weather.utils.ResourceUtil
import com.goat.weather.utils.StringUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: BaseActivity<MainPresenter, MainContract.IView>(), MainContract.IView {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.requestLocationPermission(this)
    }

    override fun loadCurrentDayData(data: DayDataModel) {
        var imgName = if (data.icon?.contains("-") == true) data.icon?.replace("-", "_") else data.icon
        if (!StringUtil.isEmpty(imgName)) {
            var drawableResId = ResourceUtil.getMipmapResId("ic_$imgName", applicationContext)
            if (drawableResId ?: 0 > 0) {
                ivWeatherIcon.setImageResource(drawableResId!!)
            }
        }
        tvTemperature.text = data.temperatureHigh?.toString() + StringUtil.FORWARD_SLASH + data.temperatureLow?.toString()
        var timeStr = if (data.time ?: 0 > 0)
            DateUtil.timestampToDateStr(data.time!! * 1000, DateUtil.TIME_WITH_FORMAT)
        else StringUtil.EMPTY
        tvTime.text = timeStr
    }

    override fun loadDataFailed(errorMsgId: Int) {
        Toast.makeText(this, resources.getString(errorMsgId), Toast.LENGTH_LONG).show()
    }

    override fun showLocationDisallow(resId: Int) {
        Toast.makeText(this, resources.getString(resId), Toast.LENGTH_LONG).show()
    }

}