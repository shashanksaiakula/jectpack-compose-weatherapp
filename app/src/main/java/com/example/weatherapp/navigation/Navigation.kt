package com.example.weatherapp.navigation

sealed class Navigation(val route :String) {
    object Login :Navigation("login")
    object SignUp :Navigation("sign up")
    data class ForgotPassword(val emai: String) : Navigation("Forgotpassword/$emai")
    object weather :Navigation("home")
}