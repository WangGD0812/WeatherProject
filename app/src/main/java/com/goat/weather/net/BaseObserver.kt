package com.goat.weather.net

import android.util.Log
import com.goat.weather.BuildConfig
import com.goat.weather.R
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseObserver<T>: Observer<T> {

    companion object {
        const val TAG = "WeatherProject"
        const val DISPLAY_MSG_SERVER_EXCEPTION = R.string.error_display_msg_server_exception
    }

    abstract fun onSuccess(data: T)

    abstract fun onFailure(errorDisplayMsgId: Int)

    override fun onComplete() {}

    override fun onSubscribe(d: Disposable) {}

    protected open fun validateData(data: T): Boolean {
        return data != null
    }

    override fun onNext(data: T) {
        if (validateData(data)) {
            try {
                onSuccess(data)
            } catch (e: Exception) {
                onError(e)
            }
        } else {
            onFailure(DISPLAY_MSG_SERVER_EXCEPTION)
        }
    }

    override fun onError(t: Throwable) {
        if (BuildConfig.DEBUG) {
            t.message?.let {
                Log.e(TAG, it)
            }
        }
        onFailure(DISPLAY_MSG_SERVER_EXCEPTION)
    }

}