package com.idris.MyFinance.ui.theme.screens.AboutScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.idris.MyFinance.R

@Composable
fun AboutScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A82FB), Color(0xFFFC5C7D)) // Gradient background
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .padding(top = 16.dp, bottom = 16.dp)
            )

            Text(
                text = "About MyFinance",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(20.dp)) // Enhanced card shadow
                    .clip(RoundedCornerShape(20.dp)), // Rounded corners for the card
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "Illustration",
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .shadow(8.dp, RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "MyFinance helps you track expenses, manage budgets, and understand your financial habits with ease.",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF616161)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text("Developed by: Idris Abdi Derow", fontSize = 16.sp, color = Color(0xFF616161))
                    Text("Email: idris@MyFinance.com", fontSize = 16.sp, color = Color(0xFF616161))
                    Text("Phone: +254 741 879051", fontSize = 16.sp, color = Color(0xFF616161))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shadow(8.dp, RoundedCornerShape(12.dp)), // Button with rounded corners and shadow
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
            ) {
                Text(text = "Back to Home", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutScreen() {
    AboutScreen(rememberNavController())
}
