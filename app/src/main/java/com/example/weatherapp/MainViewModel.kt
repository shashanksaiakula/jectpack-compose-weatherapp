package com.example.weatherapp

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.UiState
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.data.Weather
import com.example.weatherapp.utils.ErrorHandler
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.launch



class MainViewModel : ViewModel() {

    private val _uiState = MutableLiveData<UiState<Weather>>(UiState.Loading)
    val uiState: LiveData<UiState<Weather>> get() = _uiState
    private val _count = MutableLiveData<Int>(0)
    val count: LiveData<Int> get() = _count
    private val _login = MutableLiveData<String>()
    val login :LiveData<String> get() = _login
    private lateinit var auth: FirebaseAuth
    private val TAG = "check"
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
                    errorHandler.handleApiError(
                        weatherRepository.code(),
                        weatherRepository.message()
                    )
                }
            } catch (e: Exception) {
                Log.e("check", "getCity: ${e.toString()}")
                errorHandler.handleApiError(500, e.message.toString())
            }
        }
    }
    fun checkLogin(activity :Activity,email :String,password :String) {
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser;
                    Log.d(TAG, "signInWithEmail:success ${Gson().toJson(user?.email)}")
                    _login.value = "sucess"
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    _login.value = "fail"

                }
            }
    }
}