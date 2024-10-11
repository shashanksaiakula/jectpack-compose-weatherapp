package com.example.weatherapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.api.UiState
import com.example.weatherapp.data.Weather
import com.example.weatherapp.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun Weather(mainViewModel: MainViewModel,navController: NavController) {


    val uiState by mainViewModel.uiState.observeAsState(UiState.Loading)
    val keyboardController = LocalSoftwareKeyboardController.current
    var enterValue by remember {
        mutableStateOf("")
    }
    var load by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()
    val auth : FirebaseAuth = Firebase.auth

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(5.dp)
            .verticalScroll(scrollState),
    ) {

        Row(
            modifier = Modifier.padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = enterValue,
                onValueChange = {
                    enterValue = it
                },
                label = {
                    Text("enter city name")
                }
            )
            Image((Icons.Default.Search), contentDescription = "search",
                modifier = Modifier
//                    .background(Color.Red)
                    .padding(10.dp)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(15.dp)) // Change this to your desired border width and color
                    .padding(10.dp)
                    .clickable {
                        Log.e("check", "Weather: $enterValue")
                        mainViewModel.getCity(enterValue)
                        load = !load
                        keyboardController?.hide()
                    })
        }
        when (uiState) {
            is UiState.Loading -> {
                if (load)
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                    }
            }

            is UiState.Success -> {
                val weather = (uiState as UiState.Success<Weather>).data
                // Display the weather data
                DisplayData(weather)
            }

            is UiState.Error -> {
                val errorMessage = (uiState as UiState.Error).message
                Text(text = errorMessage, color = Color.Red)
            }
        }
        CustomButton(modifier = Modifier, buttonText = "Logout") {
            auth.signOut()
            navController.navigate(Navigation.Login.route)
        }

    }

}

@Composable
fun DisplayData(weather: Weather) {
    Log.e("check", "DisplayData: $weather")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.LocationOn, contentDescription = "",
//                modifier = Modifier.padding(5.dp))
            )
            Text(
                "${weather.location.country} , ${weather.location.region}",
                fontSize = 20.sp,
            )
        }
        Text(
            text = "${weather.current.temp_c} °C",
            fontWeight = FontWeight.Bold,
            fontSize = 70.sp,
            modifier = Modifier.padding(30.dp),
            textAlign = TextAlign.Center,
        )
//    Image(painter = painterResource(R))
        var img = weather.current.condition.icon
        img = "https:" + img.replace("64x64", "128x128")
//        Log.e("check", "DisplayData: $img")
        AsyncImage(
            model = img,
            modifier = Modifier
                .size(150.dp),
            contentDescription = "",
//            error = painterResource(id = R.drawable.error_placeholder) // Use a placeholder image
        )

        Text(weather.current.condition.text)

        Column(
            modifier = Modifier
                .padding(5.dp)
                .background(Color.LightGray)
                .fillMaxWidth()
                .border(0.dp, Color.Transparent, shape = RoundedCornerShape(20.dp)),


            ) {
            ShowData(weather.current.humidity, "Humidity", weather.current.wind_kph, "wind speed")
            ShowData(weather.current.uv, "uv", weather.current.precip_mm, "precip mm")
            val formattedTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(weather.location.localtime_epoch),
                ZoneId.systemDefault()
            ).format(
                DateTimeFormatter.ofPattern("hh:mm:ss")
            )
            ShowData(
                weather.location.localtime.substring(0, weather.location.localtime.length - 5),
                "Date",
                formattedTime,
                "Time"
            )
        }
    }
}

@Composable
fun ShowData(valu1: String, valuType1: String, valu2: String, valuType2: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                valu1, fontWeight = FontWeight.Bold
            )
            Text(valuType1)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                valu2, fontWeight = FontWeight.Bold
            )
            Text(valuType2)
        }
    }
}

