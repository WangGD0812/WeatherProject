package com.goat.weather.tasks.main

import com.goat.weather.base.BasePresenter
import com.goat.weather.base.IBaseView
import com.goat.weather.model.DayDataModel

object MainContract {

    interface IView: IBaseView {
        fun loadCurrentDayData(data: DayDataModel)
        fun loadDataFailed(error: String?)
        fun showLocationDisallow(resId: Int)
    }

    abstract class Presenter: BasePresenter<IView>() {
        abstract fun requestWeatherData()
    }

}