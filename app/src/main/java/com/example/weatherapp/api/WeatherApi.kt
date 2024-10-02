package com.example.weatherapp.api

import com.example.weatherapp.data.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/current.json")
    suspend fun getWeatherData(@Query("key") Key: String,
                               @Query("q") city :String): Response<Weather>
}