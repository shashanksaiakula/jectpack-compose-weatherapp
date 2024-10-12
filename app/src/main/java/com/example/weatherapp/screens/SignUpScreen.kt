package com.example.weatherapp.screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.navigation.Navigation

@Composable
fun SignUpScreen(navController: NavController, viewModel: MainViewModel, activity: Activity) {


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var conformPassword by remember { mutableStateOf("") }
    val account by viewModel.createAccount.observeAsState()
    var isCreate by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize() // Takes up the whole screen
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(), // Makes the column take up full screen height
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            InputField("Email", Icons.Default.Email, false, email) {
                email = it
            }
            InputField("Password", Icons.Default.Lock, true, password) {
                password = it
            }
            InputField("Confirm Password", Icons.Default.Lock, true, conformPassword) {
                conformPassword = it
            }
        }

        // Place the button at the bottom of the screen
        CustomButton(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Aligns button at the bottom center
                .padding(16.dp), // Padding from the bottom of the screen
            buttonText = "Sign up"
        ) {
            viewModel.signUp(activity, email, password)
        }
    }
    account.let {
        if (!isCreate)
            when (it) {
                "Sucesses" -> {
                    navController.navigate(Navigation.Login.route)
                    isCreate = true
                }

                "Fail" -> {
                    Toast.makeText(activity, "account creatin failed", Toast.LENGTH_SHORT).show()
                    isCreate = true
                }
            }
    }
}
