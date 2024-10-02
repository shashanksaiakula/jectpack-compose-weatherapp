package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.UiState
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.data.Weather
import com.example.weatherapp.utils.ErrorHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _uiState = MutableLiveData<UiState<Weather>>(UiState.Loading)
    val uiState: LiveData<UiState<Weather>> get() = _uiState
    private val _count = MutableLiveData<Int>(0)
    val count: LiveData<Int> get() = _count

    private val _weather = MutableLiveData<Weather?>()
    val weather: MutableLiveData<Weather?> get() = _weather

    fun increasCount() {
        _count.value = _count.value?.plus(1)
    }

    private val errorHandler = ErrorHandler<Weather> { newState ->
        _uiState.value = newState // Update the UI state
    }

    fun getCity(city: String) {
        viewModelScope.launch {
            try {
            var weatherRepository =
                WeatherRepository().getWeatherData("93fadba2481948149e6175601242509", city)
            if (weatherRepository.isSuccessful) {
                val weatherData = weatherRepository.body() // This will be of type Weather?
                if (weatherData != null) {
                    _uiState.value = UiState.Success(weatherData)
                } else {
                    errorHandler.handleApiError(404, "No weather data found")
                }
            } else {
                errorHandler.handleApiError(weatherRepository.code(), weatherRepository.message())
            }
        }catch (e: Exception){
                Log.e("check", "getCity: ${e.toString()}", )
                errorHandler.handleApiError(500, e.message.toString())
        }
    }
        }
}