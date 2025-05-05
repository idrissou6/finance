package com.idris.myfinanace.ui.theme.screens.SplashScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.idris.myfinanace.navigation.ROUTE_HOME
import androidx.navigation.compose.rememberNavController
import com.idris.myfinance.R

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(true) {
        scale.animateTo(1f, animationSpec = tween(durationMillis = 1500))
        delay(2000)
        navController.navigate(ROUTE_HOME) {
            popUpTo(0)
        }
    }

    // Load the splash screen background image
    val backgroundImage = painterResource(id = R.drawable.money) // Replace with your image

    Box(modifier = Modifier.fillMaxSize()) {
        // Display the background image
        Image(painter = backgroundImage, contentDescription = null, modifier = Modifier.fillMaxSize())

        // Gradient overlay to enhance text visibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.4f), Color.Transparent)
                    )
                )
        )

        // Overlay content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Logo / App Name with animation
            Text(
                text = "MyFinance",
                fontSize = 48.sp,
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.scale(scale.value)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subheading with a smooth fade-in effect
            Text(
                text = "Your Personal Finance Tracker",
                color = Color.LightGray,
                fontSize = 18.sp,
                style = TextStyle(fontWeight = FontWeight.Light),
                modifier = Modifier.alpha(0.8f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Optional: Add a small loading spinner or icon
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen(rememberNavController())
}

