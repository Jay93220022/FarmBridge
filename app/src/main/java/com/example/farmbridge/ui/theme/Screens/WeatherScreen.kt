package com.example.farmbridge.ui.theme.Screens
import androidx.compose.ui.res.stringResource


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.farmbridge.ui.theme.ViewModels.WeatherViewModel
import com.example.farmbridge.ui.theme.api.NetworkResponse

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.farmbridge.ui.theme.api.WeatherModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.uitheme.FarmBridgeTheme

import java.io.BufferedReader
import java.io.InputStreamReader
import androidx.compose.material.icons.filled.Close as Close

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel, languageViewModel: LanguageViewModel, modifier: Modifier) {

    FarmBridgeTheme {


    var city by remember { mutableStateOf("") }
    var showSuggestions by remember { mutableStateOf(false) }
    val suggestions = remember { mutableStateListOf<String>() }
    val cities = remember { mutableStateListOf<String>() }
    val weatherResult = weatherViewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val currentLanguage = languageViewModel.currentLanguage.collectAsState(initial = "English").value

    // Load cities from CSV file - reading from column 2
    LaunchedEffect(Unit) {
        try {
            context.assets.open("indian_cities.csv").use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).useLines { lines ->
                    cities.addAll(
                        lines.mapNotNull { line ->
                            try {
                                line.split(",").getOrNull(1)?.trim()?.takeIf { it.isNotEmpty() }
                            } catch (e: Exception) {
                                null // Skip malformed lines
                            }
                        }.distinct() // Remove duplicates if any
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged { focusState ->
                            showSuggestions = focusState.isFocused && city.isNotEmpty()
                        },
                    value = city,
                    singleLine = true,
                    onValueChange = { newValue ->
                        city = newValue
                        if (newValue.isNotEmpty()) {
                            showSuggestions = true
                            suggestions.clear()
                            suggestions.addAll(
                                cities.filter {
                                    it.contains(newValue, ignoreCase = true)
                                }.take(5)
                            )
                        } else {
                            showSuggestions = false
                            suggestions.clear()
                        }
                    },
                    
                    label = {
                        Text(
                            text = when (currentLanguage) {
                                "Hindi" -> "स्थान खोजें"
                                "Marathi" -> "स्थान शोधा"
                                else -> "Search for any Location"
                            }
                        )
                    },
                    trailingIcon = {
                        if (city.isNotEmpty()) {
                            IconButton(onClick = { city = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close, // Use a close/cancel icon
                                    contentDescription = "Clear text"
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF006838),
                        focusedLabelColor = Color(0xFF006838),
                        cursorColor = Color(0xFF006838),
                        focusedTextColor = Color(0xFF4CAF50),
                        unfocusedTextColor = Color(0xFF4CAF50)
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            showSuggestions = false
                            weatherViewModel.getData(city)
                            keyboardController?.hide()
                        }
                    )
                )
                IconButton(
                    onClick = {
                        showSuggestions = false
                        weatherViewModel.getData(city)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            }

            if (showSuggestions && suggestions.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 200.dp)
                    ) {
                        items(suggestions) { suggestion ->
                            Text(
                                text = suggestion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        city = suggestion
                                        showSuggestions = false
                                        weatherViewModel.getData(suggestion)
                                        keyboardController?.hide()
                                    }
                                    .padding(16.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (suggestion != suggestions.last()) {
                                Divider(color = MaterialTheme.colorScheme.outlineVariant)
                            }
                        }
                    }
                }
            }

            when (val result = weatherResult.value) {
                is NetworkResponse.Error -> {
                    Text(text = result.message)
                }
                NetworkResponse.Loading -> {
                    CircularProgressIndicator(
                        color = Color(0xFF006838)
                    )
                }
                is NetworkResponse.Success -> {
                    WeatherDetails(data = result.data, currentLanguage = currentLanguage)
                }
                null -> Text(
                    text = when (currentLanguage) {
                        "Hindi" -> "कृपया एक स्थान दर्ज करें"
                        "Marathi" -> "कृपया एक स्थान प्रविष्ट करा"
                        else -> "Enter a location to get weather data"
                    }
                )
            }
        }
    }
}}

@Composable
fun WeatherDetails(data: WeatherModel, currentLanguage: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = " ",
                Modifier.size(40.dp)
            )
            Text(
                text = data.location.name,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = data.location.country,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${data.current.temp_c} °C ",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        AsyncImage(model = "https:${data.current.condition.icon}".replace("64x64","128x128"),
            contentDescription = "", modifier = Modifier.size(160.dp))

        Text(
            text = when (currentLanguage) {
                "Hindi" -> "${data.current.condition.text} "
                "Marathi" -> "${data.current.condition.text} "
                else -> "${data.current.condition.text}"
            },
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))
        Card {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal(
                        key = when (currentLanguage) {
                            "Hindi" -> "नमी"
                            "Marathi" -> "आर्द्रता"
                            else -> "Humidity"
                        },
                        value = data.current.humidity
                    )
                    WeatherKeyVal(
                        key = when (currentLanguage) {
                            "Hindi" -> "हवा की गति"
                            "Marathi" -> "वाऱ्याची गती"
                            else -> "Wind Speed"
                        },
                        value = "${data.current.wind_kph} kmph"
                    )
                }

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal(
                        key = when (currentLanguage) {
                            "Hindi" -> "UV"
                            "Marathi" -> "यूव्ही"
                            else -> "UV"
                        },
                        value = data.current.uv
                    )
                    WeatherKeyVal(
                        key = when (currentLanguage) {
                            "Hindi" -> "वर्षा"
                            "Marathi" -> "पर्जन्य"
                            else -> "Precipitation"
                        },
                        value = "${data.current.precip_mm} mm"
                    )
                }

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal(
                        key = when (currentLanguage) {
                            "Hindi" -> "दृश्यता"
                            "Marathi" -> "दृश्यमानता"
                            else -> "Visibility"
                        },
                        value = "${data.current.vis_km} km"
                    )
                    WeatherKeyVal(
                        key = when (currentLanguage) {
                            "Hindi" -> "वायु दाब"
                            "Marathi" -> "वायू दाब"
                            else -> "Air Pressure"
                        },
                        value = "${data.current.is_day}"
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherKeyVal(key: String, value: String) {
    Column(
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}
