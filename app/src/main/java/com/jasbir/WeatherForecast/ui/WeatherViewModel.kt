package com.jasbir.WeatherForecast.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasbir.WeatherForecast.data.dataclass.currenttemp.WeatherData
import com.jasbir.WeatherForecast.data.dataclass.forcast.ForecastResponse
import com.jasbir.WeatherForecast.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(private val savedStateHandle: SavedStateHandle,
            private val mainRepo: MainRepository) : ViewModel() {
    val getWeatherLiveData: MutableLiveData<WeatherData> = MutableLiveData()
    val getForeCastLiveData: MutableLiveData<ForecastResponse> = MutableLiveData()
    var city : String  = ""

    fun invokeWeather(){
        viewModelScope.launch {
            getWeatherLiveData.value = mainRepo.getWeather(city)
            getForeCastLiveData.value = mainRepo.getForeCast(city)
        }
    }

}




