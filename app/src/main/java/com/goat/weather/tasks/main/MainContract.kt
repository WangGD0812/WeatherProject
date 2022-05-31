package com.goat.weather.tasks.main

import android.content.Context
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
         * Get the location permission grant status, if not granted, apply for permission.
         */
        abstract fun requestLocationPermission(activity: FragmentActivity)
    }

}