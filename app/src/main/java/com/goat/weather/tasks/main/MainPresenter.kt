package com.goat.weather.tasks.main

import com.goat.weather.model.Blocks
import com.goat.weather.model.WheaterDataModel
import com.goat.weather.net.ApiHeader
import com.goat.weather.net.BaseObserver
import com.goat.weather.net.RetrofitModule
import com.goat.weather.net.WeatherApiService
import com.goat.weather.utils.StringUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainPresenter : MainContract.Presenter() {

    companion object {
        private const val KEY_EXCLUDE = "exclude"
        private const val KEY_LANG = "lang"
        private const val KEY_UNITS = "units"

        private const val LANG_ZH = "zh"
        private const val UNIT_AUTO = "auto"
    }

    override fun requestWeatherData() {
        val lat = 31.104097
        val lon = 121.229686
        val time: Long = 1653842762
        val blocks = Blocks.CURRENTLY.block + StringUtil.COMMA + Blocks.FLAGS.block

        val requestParam = HashMap<String, String>()
        requestParam[KEY_EXCLUDE] = blocks
        requestParam[KEY_LANG] = LANG_ZH
        requestParam[KEY_UNITS] = UNIT_AUTO
        RetrofitModule.getInstance()
            .create(WeatherApiService::class.java)
            .getWeatherData(ApiHeader.getCommonHeader(), lat, lon, time, requestParam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<WheaterDataModel?>() {
                override fun onSuccess(data: WheaterDataModel?) {
                    data?.daily?.data?.get(0)?.let {
                        mView?.loadCurrentDayData(it)
                    }
                }

                override fun onFailure(code: Int, errorMsg: String?) {
                    mView?.loadDataFailed(errorMsg)
                }
            })

    }
}