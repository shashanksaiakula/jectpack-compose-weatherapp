package com.example.weatherapp.data

import okhttp3.ResponseBody

data class WeatherApiResponse(
    val code: Int,
    val result: Result<Weather>
)