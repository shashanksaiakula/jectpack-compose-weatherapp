package com.example.weatherapp.screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel, activity: Activity) {
//    val coroutineScope = rememberCoroutineScope()
    val loginState by viewModel.login.observeAsState()
    var hasHandledLogin by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(), // Makes the column take up full screen height
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            InputField("Email", Icons.Default.Email, false,email){
                email = it
            }
            InputField("Password", Icons.Default.Lock, true,password){
                password = it
            }
            TextUnderLine("Forgot Password") {
                navController.navigate(Navigation.ForgotPassword(emai = email).route)
            }
            CustomButton(
                modifier = Modifier // Aligns button at the bottom center
                    .padding(16.dp), // Padding from the bottom of the screen
                buttonText = "Sign up"
            ) {
//                coroutineScope.launch {
            navController.navigate(Navigation.SignUp.route)
//                }
            }
        }
        CustomButton(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(16.dp), "Login"
        ) {
                    viewModel.checkLogin(activity,email,password)
        }
    }
    loginState?.let {
        if(!hasHandledLogin)
        when (it) {
            "sucess" -> {
                navController.navigate(Navigation.weather.route)
                hasHandledLogin = true
            }
            "fail" -> {
                Toast.makeText(activity, "please try again", Toast.LENGTH_SHORT).show()
                hasHandledLogin = true
            }
        }
    }
    val auth = Firebase.auth
    val user = auth.currentUser
    if(user !== null && !hasHandledLogin) {
        navController.navigate(Navigation.weather.route)
        hasHandledLogin = true
    }
}

@Composable
fun InputField(value: String, leadIcon: ImageVector, isPassWord: Boolean,textValue: String, onValueChage :(String)->Unit) {

    var visible by remember {
        mutableStateOf(false)
    }

    Row {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            value = textValue,
            onValueChange = onValueChage,
            label = {
                Text(value)
            },
            leadingIcon = {
                Icon(imageVector = leadIcon, "des")
            },
            visualTransformation = if (isPassWord && !visible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassWord) {
                    val icon = if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (visible) "Hide password" else "Show password"

                    Icon(
                        imageVector = icon,
                        contentDescription = description,
                        modifier = Modifier.clickable {
                            visible = !visible
                        } // Toggles visibility on click
                    )
                }
            },
            maxLines = 2,

            )
    }
}

@Composable
fun TextUnderLine(text: String, forgotNav: () -> Unit) {
    Text(text = text,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            forgotNav()
        }
    )
}

@Composable
fun CustomButton(modifier: Modifier, buttonText: String, click: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(50.dp),
        onClick = click
    ) {
        Text(buttonText)
    }
}