package com.idris.myfinanace.ui.theme.Screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.idris.myfinanace.navigation.ROUTE_ADD_EXPENSE
import com.idris.myfinanace.navigation.ROUTE_EXPENSE_LIST

@Composable
fun HomeScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "HOME SCREEN",
            color = Color.Red,
            fontSize = 30.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Text(
            text = "Click to enter",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        val buttonColor = Color(0xFF6200EE)

        Button(
            onClick = { navController.navigate(ROUTE_ADD_EXPENSE) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        ) {
            Text(
                text = "Add Expense",
                color = Color.White,
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = { navController.navigate(ROUTE_EXPENSE_LIST) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        ) {
            Text(
                text = "View Expenses",
                color = Color.White,
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview()
@Composable
fun PreviewHomeScreen() {
    HomeScreen(rememberNavController())
}
