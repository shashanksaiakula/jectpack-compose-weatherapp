package com.example.weatherapp.navigation
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.screens.ForgotPasswordScreen
import com.example.weatherapp.screens.LoginScreen
import com.example.weatherapp.screens.SignUpScreen
import com.example.weatherapp.screens.Weather

@Composable
fun MyNavigation(viewModel: MainViewModel, activity :Activity) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navigation.Login.route){
        composable(Navigation.Login.route) {
            LoginScreen(navController,viewModel,activity)
        }
        composable(Navigation.SignUp.route) {
            SignUpScreen(navController,viewModel,activity)
        }
        composable(Navigation.weather.route) {
            Weather(viewModel,navController)
        }
        composable("Forgotpassword/{emai}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("emai")
//            if (email != null) {
                ForgotPasswordScreen(navController, email!!,activity,viewModel)
//            }
        }

    }
}

