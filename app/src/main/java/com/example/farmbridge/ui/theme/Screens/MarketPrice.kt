import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmbridge.R
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.ViewModels.MarketViewModel
import com.example.farmbridge.ui.theme.uitheme.FarmBridgeTheme

@Composable
fun MarketPrice(marketViewModel: MarketViewModel,languageViewModel: LanguageViewModel) {
    val marketItems = marketViewModel.state.value // Now a list of MarketItems
    val currentLanguage = languageViewModel.currentLanguage.collectAsState(initial = "English").value
    FarmBridgeTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            Image(painter = painterResource(id= R.drawable.logo),
                contentDescription = " ",
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                )
         Text("Market Price List",
             color = MaterialTheme.colorScheme.onBackground,
             fontWeight = FontWeight.Bold,
             fontSize = 24.sp)
            for (item in marketItems) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF4CAF50)
                    )


                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start, // Align the content to the start
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text( text = when (currentLanguage) {
                            "Hindi" -> "फसल: ${item.cropName}"
                            "Marathi" -> "पीक: ${item.cropName}"
                            else -> "Crop: ${item.cropName}"
                        }, fontWeight = FontWeight.Bold)
                        Text( text = when (currentLanguage) {
                            "Hindi" -> "मूल्य: ${item.price}"
                            "Marathi" -> "किंमत: ${item.price}"
                            else -> "Price: ${item.price}"
                        },fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(70.dp))
        }

    }
}