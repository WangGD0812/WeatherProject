package com.goat.weather.base

interface IPresenter<in V: IView> {

    /**
     * interact with View
     */
    fun attachView(view: V)

    /**
     * release resources
     */
    fun detachView()

}