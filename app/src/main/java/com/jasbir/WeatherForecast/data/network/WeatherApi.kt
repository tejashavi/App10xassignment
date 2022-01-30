package com.jasbir.WeatherForecast.data.network

import com.jasbir.WeatherForecast.data.dataclass.currenttemp.WeatherData
import com.jasbir.WeatherForecast.data.dataclass.forcast.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather?APPID=9b8cb8c7f11c077f8c4e217974d9ee40&units=metric")
   suspend fun getWeather(
        @Query("q") city :String
    ):Response<WeatherData>

    @GET("/data/2.5/forecast?APPID=9b8cb8c7f11c077f8c4e217974d9ee40&units=metric")
    suspend fun getForeCast(
        @Query("q") city :String
    ):Response<ForecastResponse>

/*@GET("/data/2.5/weather?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40")
suspend fun getWeather():Response<WeatherData>*/

}