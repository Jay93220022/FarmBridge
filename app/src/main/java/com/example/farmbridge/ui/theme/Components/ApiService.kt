package com.example.farmbridge.ui.theme.Components

import com.example.farmbridge.ui.theme.DataClasses.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" // Optional: Use "imperial" for Fahrenheit
    ): WeatherResponse
}
