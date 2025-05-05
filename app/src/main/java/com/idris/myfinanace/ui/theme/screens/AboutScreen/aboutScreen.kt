package com.idris.myfinanace.ui.theme.screens.AboutScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.idris.myfinance.R

@Composable
fun AboutScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "About MyFinance App",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // App Logo or Icon
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your logo
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )

        // App Description
        Text(
            text = "MyFinance is an easy-to-use app to help you track your daily expenses, create budgets, and gain insights into your financial habits.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Developer Info
        Text(
            text = "Developed by: Idris",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        // Contact Info (optional)
        Text(
            text = "Contact: idris@myfinance.com",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        // Back Button
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Back to Home", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutScreen() {
    AboutScreen(rememberNavController())
}
