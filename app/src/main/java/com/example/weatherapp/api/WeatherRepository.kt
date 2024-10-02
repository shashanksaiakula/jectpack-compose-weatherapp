package com.example.weatherapp.api

import com.example.weatherapp.data.Weather
import retrofit2.Response


class WeatherRepository {
    private val weatherApi: WeatherApi = getRetofitInstace().create(WeatherApi::class.java)
        suspend fun getWeatherData(key: String, city: String): Response<Weather> {
            return  weatherApi.getWeatherData(key,city)
        }
}
