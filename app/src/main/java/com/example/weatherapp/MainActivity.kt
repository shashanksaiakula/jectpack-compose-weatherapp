package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

private lateinit var auth: FirebaseAuth
private val TAG = "check"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        actionBar?.hide()
        // Initialize Firebase Auth
        auth = Firebase.auth
        setContent {
            val mainViewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class)
            WeatherAppTheme {
                Column {
                    Weather(mainViewModel)
                }
            checkLogin()
            }
        }
    }

    private fun checkLogin() {
        auth.signInWithEmailAndPassword("shashanksai664@gmail.com", "Sai@1234")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    updateUI(user)
                    val user = auth.currentUser
                          Log.d(TAG, "signInWithEmail:success ${Gson().toJson(user)}")
                      user.let {
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
//                    updateUI(null)
                }
            }
    }
}

