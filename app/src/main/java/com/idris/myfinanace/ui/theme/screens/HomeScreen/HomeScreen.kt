package com.idris.myfinanace.ui.theme.Screens.HomeScreen



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.idris.myfinanace.navigation.ROUTE_ADD_EXPENSE
import com.idris.myfinanace.navigation.ROUTE_EXPENSE_LIST
import androidx.compose.ui.res.painterResource
import com.idris.myfinance.R

@Composable
fun HomeScreen(navController: NavHostController) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundImage = painterResource(id = R.drawable.money) // Replace with your image

    Box(modifier = Modifier.fillMaxSize()) {
        // Display the background image
        Image(painter = backgroundImage, contentDescription = null, modifier = Modifier.fillMaxSize())

        // Overlay content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "MyFinance",
                color = primaryColor,
                fontSize = 36.sp,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Track your expenses efficiently.",
                color = Color.LightGray,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate(ROUTE_ADD_EXPENSE) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text(text = "➕ Add Expense", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(ROUTE_EXPENSE_LIST) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC6))
            ) {
                Text(text = "📋 View Expenses", color = Color.Black, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(rememberNavController())
}



