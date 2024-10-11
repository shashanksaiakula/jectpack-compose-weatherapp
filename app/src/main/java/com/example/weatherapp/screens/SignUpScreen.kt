package com.example.weatherapp.screens

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.navigation.Navigation

@Composable
fun SignUpScreen(navController: NavController) {
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
            InputField("Email", Icons.Default.Email, false)
            InputField("Password", Icons.Default.Lock, true)
            InputField("Confirm Password", Icons.Default.Lock, true)
//            TextUnderLine("Forgot Password")
        }

        // Place the button at the bottom of the screen
        CustomButton(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Aligns button at the bottom center
                .padding(16.dp), // Padding from the bottom of the screen
            buttonText = "Sign up"
        ) {
            navController.navigate(Navigation.Login.route)
        }
    }
}
