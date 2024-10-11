package com.example.weatherapp.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.navigation.Navigation

@Composable
fun LoginScreen(navController: NavController) {

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
            InputField("Email", Icons.Default.Email, false)
            InputField("Password", Icons.Default.Lock, true)
            TextUnderLine("Forgot Password"){
                navController.navigate(Navigation.ForgotPassword(emai = "test@example.com").route)
            }
            CustomButton(
                modifier = Modifier // Aligns button at the bottom center
                    .padding(16.dp), // Padding from the bottom of the screen
                buttonText = "Sign up"
            ) {
                navController.navigate(Navigation.SignUp.route)
            }
        }
        CustomButton(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(16.dp), "Login"
        ) {
            navController.navigate("home")
        }
    }
}

@Composable
fun InputField(value: String, leadIcon: ImageVector, isPassWord: Boolean) {
    var input by remember {
        mutableStateOf("")
    }
    var visible by remember {
        mutableStateOf(false)
    }

    Row {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            value = input,
            onValueChange = {
                input = it
            },
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
fun TextUnderLine(text: String ,forgotNav :()-> Unit) {
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