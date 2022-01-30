package com.jasbir.WeatherForecast.data.dataclass.forcast

data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ListData>,
    val message: Int
)