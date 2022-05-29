package com.goat.weather.net

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

object ApiProxyFactory {

    private const val TIME_OUT: Long = 10
    private val proxies = ConcurrentHashMap<String, Any>()
    private const val SECRET_KEY = "37788829244c9eb24dfea7e860e00e69"
    private const val BASE_URL: String = "https://api.darksky.net/forecast/${SECRET_KEY}/"

    @JvmOverloads
    fun <T> getProxy(api: Class<T>,
                     interceptors: Array<Interceptor>? = null,
                     jsonConverterFactory: Converter.Factory? = null): T {
        val key = BASE_URL + api.name
        if (proxies.containsKey(key)) {
            return proxies[key] as T
        }
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(interceptors))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(jsonConverterFactory ?: GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val proxy = client.create(api)
        proxies[key] = proxy as Any
        return proxy
    }

    private fun getOkHttpClient(interceptors: Array<Interceptor>?): OkHttpClient {
        val okHttpBuilder = OkHttpClient().newBuilder()
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        if (interceptors != null) {
            for (interceptor in interceptors) {
                okHttpBuilder.addInterceptor(interceptor)
            }
        }
        return okHttpBuilder.build()
    }

}