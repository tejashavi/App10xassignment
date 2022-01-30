package com.jasbir.WeatherForecast.data.dataclass.forcast

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)