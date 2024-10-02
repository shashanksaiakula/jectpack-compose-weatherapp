package com.example.weatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRetofitInstace() :Retrofit{
    return Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}