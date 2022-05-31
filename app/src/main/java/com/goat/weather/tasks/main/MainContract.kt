package com.goat.weather.tasks.main

import androidx.fragment.app.FragmentActivity
import com.goat.weather.base.BasePresenter
import com.goat.weather.base.IBaseView
import com.goat.weather.model.DayDataModel

object MainContract {

    interface IView: IBaseView {
        fun loadCurrentDayData(data: DayDataModel)
        fun loadDataFailed(errorMsgId: Int)
        fun showLocationDisallow(resId: Int)
    }

    abstract class Presenter: BasePresenter<IView>() {

        /**
         * request weather api to get data
         */
        abstract fun requestWeatherData(activity: FragmentActivity)

        /**
         * Get the location permission grant status, if not granted, apply for permission.
         */
        abstract fun requestLocationPermission(activity: FragmentActivity)

        abstract fun jumpToHourlyDetailsPage()
    }

}