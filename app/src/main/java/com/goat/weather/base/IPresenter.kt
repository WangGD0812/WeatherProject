package com.goat.weather.base

interface IPresenter<in V: IBaseView> {

    /**
     * interact with View
     */
    fun attachView(view: V)

    /**
     * free resources
     */
    fun detachView()

}