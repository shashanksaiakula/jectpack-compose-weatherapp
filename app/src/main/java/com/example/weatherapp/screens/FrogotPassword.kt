package com.example.weatherapp.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.navigation.Navigation

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    string: String = "",
    activity: Activity,
    viewModel: MainViewModel
) {

    var email by remember { mutableStateOf(string) }
    val changePassword by viewModel.changePassword.observeAsState()
    var isChangePassword by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            InputField("Email", Icons.Default.Email, false, email) {
                email = it
            }
            CustomButton(modifier = Modifier, buttonText = "Submit") {
//                Toast.makeText(activity, email, Toast.LENGTH_SHORT).show()

                viewModel.changePassword(activity, email)
            }
//            Text(string)
        }
    }
    changePassword.let {
        if(!isChangePassword)
        when(it){
            "Sucesses" ->{
                navController.navigate(Navigation.Login.route)
                isChangePassword = true
            }
            "Fail" ->{
                Toast.makeText(activity, "please try again", Toast.LENGTH_SHORT).show()
                isChangePassword = true
            }
        }
    }

}