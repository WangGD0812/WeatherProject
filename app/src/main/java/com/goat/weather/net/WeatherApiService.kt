package com.goat.weather.net

import com.goat.weather.data.model.WheatherDataModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface WeatherApiService {

    @GET("{latitude},{longitude},{time}")
    suspend fun getWeatherData(@HeaderMap headers: Map<String, String>,
                                        @Path("latitude") latitude: Double,
                                        @Path("longitude") longitude: Double,
                                        @Path("time") time: Long,
                                        @QueryMap paramsMap: Map<String, String>) : Observable<WheatherDataModel>
}