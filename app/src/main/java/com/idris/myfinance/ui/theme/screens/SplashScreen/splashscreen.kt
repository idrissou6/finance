package com.idris.MyFinance.ui.theme.screens.SplashScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.idris.MyFinance.R
import com.idris.MyFinance.navigation.ROUTE_HOME
import com.idris.MyFinance.navigation.ROUTE_LOGIN
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember { Animatable(0f) }
    val logoAlpha = remember { Animatable(0f) }
    val progressAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {

        scale.animateTo(1f, animationSpec = tween(1000, easing = EaseOutBack))
        logoAlpha.animateTo(1f, animationSpec = tween(1000))
        progressAlpha.animateTo(1f, animationSpec = tween(1500))


        delay(2500)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            navController.navigate(ROUTE_HOME) { popUpTo(0) }
        } else {
            navController.navigate(ROUTE_LOGIN) { popUpTo(0) }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF512DA8), Color(0xFF673AB7))
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(140.dp)
                    .scale(scale.value)
                    .alpha(logoAlpha.value)
            )
            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "MyFinance",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )


            Text(
                text = "Your Personal Finance Tracker",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(40.dp))


            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 3.dp,
                modifier = Modifier
                    .size(32.dp)
                    .alpha(progressAlpha.value)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen(navController = rememberNavController())
}
