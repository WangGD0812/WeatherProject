package com.goat.weather.net

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface WeatherApiService {

    @GET("/{latitude},{longitude},{time}")
    fun getWeatherData(@HeaderMap headers: Map<String, String>,
                                        @Path("latitude") latitude: String,
                                        @Path("longitude") longitude: String,
                                        @Path("time") time: Long,
                                        @QueryMap paramsMap: Map<String, String>)
}