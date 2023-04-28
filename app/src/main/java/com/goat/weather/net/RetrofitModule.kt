package com.goat.weather.net

import java.io.Serializable

class RetrofitModule private constructor(): Serializable {
    companion object {
        fun getInstance(): RetrofitModule {
            return SingletonHolder.mInstance
        }
    }

    private object SingletonHolder {
        val mInstance: RetrofitModule = RetrofitModule()
    }

    private fun readResolve(): Any {
        return SingletonHolder.mInstance
    }

    fun <T> create(serviceClass: Class<T>): T {
        return ApiProxyFactory.getProxy(serviceClass)
    }
}