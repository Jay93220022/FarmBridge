package com.example.farmbridge.ui.theme.Screens

import android.view.animation.OvershootInterpolator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.farmbridge.R
import com.example.farmbridge.ui.theme.Navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun Splash(modifier: Modifier = Modifier,navController: NavController) {
val scale = remember {
    Animatable(0f)
}
    var progress by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = true) {
scale.animateTo(
    targetValue = 1.5f,
    animationSpec = tween(
        durationMillis = 1000,
        easing = {
            OvershootInterpolator(2f).getInterpolation(it)
        }
    )
)
        delay(1000L)
        navController.navigate(Screen.SelectLanguage.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }

        }
        }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription ="Logo",
            contentScale = ContentScale.Crop,
           alignment =Alignment.Center)

        Spacer(modifier = Modifier.height(224.dp))


        CircularProgressIndicator(
            color = Color(0xFF006838),
            strokeWidth = 5.dp,
            modifier = Modifier.size(40.dp)
        )


    }
}