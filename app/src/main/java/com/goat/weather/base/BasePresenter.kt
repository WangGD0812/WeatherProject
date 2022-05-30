package com.goat.weather.base

/**
 * The base class for all Presenter
 */
open class BasePresenter<T: IBaseView> : IPresenter<T> {

    var mIView: T? = null
        private set

    override fun attachView(mIView: T) {
        this.mIView = mIView
    }

    override fun detachView() {
        mIView = null
    }


}