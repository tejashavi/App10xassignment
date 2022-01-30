package com.jasbir.WeatherForecast.di

import com.jasbir.WeatherForecast.data.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideRetrofit(): WeatherApi {
        return    return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build()
                .create(WeatherApi::class.java)
    }
}