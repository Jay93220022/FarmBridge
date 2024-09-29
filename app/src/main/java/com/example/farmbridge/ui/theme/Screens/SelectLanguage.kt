package com.example.farmbridge.ui.theme.Screens
import com.example.farmbridge.R

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmbridge.ui.theme.LightGreen

@Preview
@Composable
fun SelectLanguage(modifier: Modifier = Modifier) {
    Box{
        Modifier.fillMaxSize()
    }
    Image(painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x63CC32)),
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(text = "Select Language",
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        fontStyle = FontStyle.Italic,
        textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(70.dp)
                .align(Alignment.CenterHorizontally)
               ,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color.Black),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF90EE90),)
        ) {
            Text(text = "English",
                modifier = Modifier
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentSize(Alignment.Center)
                    .padding(start = 105.dp, top = 25.dp),
                textAlign = TextAlign.Center,)
        }
 Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f) // Card will take 80% of the screen width
                .height(70.dp)
                .align(Alignment.CenterHorizontally)
            ,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color.Black),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF90EE90),)
        ) {
            Text(text = "हिंदी",
                modifier = Modifier
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentSize(Alignment.Center)
                    .padding(start = 115.dp, top = 25.dp),
                textAlign = TextAlign.Center,)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f) // Card will take 80% of the screen width
                .height(70.dp)
                .align(Alignment.CenterHorizontally)
            ,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color.Black),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF90EE90),)
        ) {
            Text(text = "मराठी",
                modifier = Modifier
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentSize(Alignment.Center)
                    .padding(start = 115.dp, top = 25.dp),
                textAlign = TextAlign.Center,)
        }
    }
}
