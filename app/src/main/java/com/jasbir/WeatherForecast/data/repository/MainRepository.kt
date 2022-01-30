package com.jasbir.WeatherForecast.data.repository

import com.jasbir.WeatherForecast.data.dataclass.currenttemp.WeatherData
import com.jasbir.WeatherForecast.data.dataclass.forcast.ForecastResponse
import com.jasbir.WeatherForecast.data.network.WeatherApi
import javax.inject.Inject


class MainRepository
@Inject
constructor(private val weatherApi: WeatherApi){

   suspend fun getWeather(city :String): WeatherData {
      val response = weatherApi.getWeather(city).body()
       return response!!
   }
    suspend fun getForeCast(city :String): ForecastResponse {
        val response = weatherApi.getForeCast(city).body()
        return response!!
    }

}