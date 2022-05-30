package com.goat.weather.net

import android.os.NetworkOnMainThreadException
import com.google.gson.JsonSyntaxException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException

abstract class BaseObserver<T>: Observer<T> {

    companion object {
        private const val RESPONSE_SOCKET_TIMEOUT_EXCEPTION_EOR = 10
        private const val RESPONSE_CONNECT_EXCEPTION_EOR = 11
        private const val RESPONSE_UNKNOWN_HOST_EXCEPTION_EOR = 12
        private const val RESPONSE_UNKNOWN_SERVICE_EXCEPTION_EOR = 13
        private const val RESPONSE_IO_EXCEPTION_EOR = 14
        private const val RESPONSE_NETWORK_ON_MAIN_THREAD_EXCEPTION_EOR = 15
        private const val RESPONSE_JSON_SYNTAX_EXCEPTION_EOR = 16
        private const val RESPONSE_DATA_NULL = -1
    }

    private var errorCode = -9999
    private var errorMsg = "Unknown error"

    abstract fun onSuccess(data: T)

    abstract fun onFailure(code: Int, errorMsg: String?)

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
            onFailure(RESPONSE_DATA_NULL, "response data is Empty")
        }
    }

    override fun onError(t: Throwable) {
        when (t) {
            is SocketTimeoutException -> {
                errorCode = RESPONSE_SOCKET_TIMEOUT_EXCEPTION_EOR
                errorMsg = "Server response times out."
            }
            is ConnectException -> {
                errorCode = RESPONSE_CONNECT_EXCEPTION_EOR
                errorMsg = "The network connection is exception,please check the network."
            }
            is UnknownHostException -> {
                errorCode = RESPONSE_UNKNOWN_HOST_EXCEPTION_EOR
                errorMsg = "Unable to resolve host,please check network connection."
            }
            is UnknownServiceException -> {
                errorCode = RESPONSE_UNKNOWN_SERVICE_EXCEPTION_EOR
                errorMsg = "Unknown server error."
            }
            is IOException -> {
                errorCode = RESPONSE_IO_EXCEPTION_EOR
                errorMsg = "No network, please check the network connection."
            }
            is NetworkOnMainThreadException -> {
                errorCode = RESPONSE_NETWORK_ON_MAIN_THREAD_EXCEPTION_EOR
                errorMsg = "Network requests cannot be made in the main thread."
            }
            is JsonSyntaxException -> {
                errorCode = RESPONSE_JSON_SYNTAX_EXCEPTION_EOR
                errorMsg = "Json parse error"
            }
            is RuntimeException -> {
                errorMsg = "Runtime Exception"
            }
        }

        onFailure(errorCode, errorMsg)
    }

}