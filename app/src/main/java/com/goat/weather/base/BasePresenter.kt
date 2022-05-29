package com.goat.weather.base

/**
 * The base class for all Presenter
 */
open class BasePresenter<T: IView> : IPresenter<T> {

    var mView: T? = null
        private set

    override fun attachView(mIView: T) {
        this.mView = mIView
    }

    override fun detachView() {
        mView = null
    }


}