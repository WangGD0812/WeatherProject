package com.goat.weather.tasks.details

import com.goat.weather.base.BasePresenter
import com.goat.weather.base.IBaseView
import com.goat.weather.model.HourDataModel

object HourlyDetailsContract {

    interface IView: IBaseView {
        fun loadHourlyData(data: List<HourDataModel>)
        fun loadHourlyDataFailed(errorMsgId: Int)
    }

    abstract class Presenter: BasePresenter<IView>() {
        abstract fun processHourlyData(list: List<HourDataModel>?)
    }

}