package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
//import com.example.weatherapp.navigation.MyApp
import com.example.weatherapp.navigation.MyNavigation
//import com.example.weatherapp.navigation.MyNavigation
import com.example.weatherapp.screens.LoginScreen
import com.example.weatherapp.screens.SignUpScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

private lateinit var auth: FirebaseAuth
private val TAG = "check"

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        actionBar?.hide()
        auth = Firebase.auth
        setContent {
            val mainViewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class)
            val navController = rememberNavController()
//
//            val configuration = LocalConfiguration.current
//
//            val screenHeight = configuration.screenHeightDp.dp
//            val screenWidth = configuration.screenWidthDp.dp
//
//            Log.e("check", "SignUpScreen: $screenWidth", )
//            Log.e("check", "SignUpScreen: $screenHeight", )
            val systemUiController = rememberSystemUiController()

            // Change the status bar color if needed
//            systemUiController.setStatusBarColor(Color.) // Transparent status bar

            // Scaffold layout or your main layout here
            WeatherAppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
//                        .background(Color.Red) // Background of your app
                        .systemBarsPadding() // Add padding so content doesn't go under system bars
                ) {
//                Column() {
//                    LoginScreen()
//                    Weather(mainViewModel)
//                    SignUpScreen()
//                    LoginScreen()
//                }
//                    ForgotPassword()
//            checkLogin()

                    MyNavigation(mainViewModel,this)
//                    MyApp()
                }
            }
        }
    }
}

