package com.goat.weather.tasks.main

import com.goat.weather.base.BasePresenter
import com.goat.weather.base.IView
import com.goat.weather.model.DayDataModel

object MainContract {

    interface View: IView {
        fun loadCurrentDayData(data: DayDataModel?)
        fun loadDataFailed(error: String?)
        fun showLocationDisallow(resId: Int)
    }

    abstract class Presenter: BasePresenter<View>() {
        abstract fun requestWeatherData()
    }

}