package com.example.weatherapp.data

data class Location(
    val country: String,
    val lat: String,
    val localtime: String,
    val localtime_epoch: Long,
    val lon: String,
    val name: String,
    val region: String,
    val tz_id: String
)