package com.idris.MyFinance.ui.theme.screens.HomeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.idris.MyFinance.R
import com.idris.MyFinance.navigation.*

@Composable
fun HomeScreen(navController: NavHostController) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.homescreen),
            contentDescription = "Background",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0x99000000), Color(0xCC000000)))))

        // 🔹 Profile Icon (Top-Right)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(
                onClick = { navController.navigate(ROUTE_USERPROFILE) },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, shape = RoundedCornerShape(50))
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "MyFinance",
                color = Color.White,
                fontSize = 34.sp,
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "Track your expenses efficiently.",
                color = Color.LightGray,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            HomeScreenButton("💵 Add Income", Color(0xFF03A9F4), Color(0xFF0288D1), Color.White) {
                navController.navigate(ROUTE_INCOME)
            }

            Spacer(modifier = Modifier.height(16.dp))

            HomeScreenButton("➕ Add Expense", Color(0xFF1EB980), Color(0xFF006D32), Color.White) {
                navController.navigate(ROUTE_ADD_EXPENSE)
            }

            Spacer(modifier = Modifier.height(16.dp))

            HomeScreenButton("📋 View Expenses", Color.White, Color.Black, Color.Black) {
                navController.navigate(ROUTE_EXPENSE_LIST)
            }

            Spacer(modifier = Modifier.height(16.dp))

            HomeScreenButton("💰 Budget Overview", Color(0xFF3700B3), Color(0xFF1A237E), Color.White) {
                navController.navigate(ROUTE_BUDGET_OVERVIEW)
            }

            Spacer(modifier = Modifier.height(16.dp))

            HomeScreenButton("ℹ️ About", Color.DarkGray, Color.Gray, Color.White) {
                navController.navigate(ROUTE_ABOUT)
            }

            Spacer(modifier = Modifier.height(32.dp))

            HomeScreenButton("🚪 Logout", Color.Red, Color(0xFFB71C1C), Color.White) {
                showLogoutDialog = true
            }
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Confirm Logout") },
                text = { Text("Are you sure you want to logout?") },
                confirmButton = {
                    TextButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(ROUTE_LOGIN) { popUpTo(0) }
                        showLogoutDialog = false
                    }) {
                        Text("Logout")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun HomeScreenButton(
    text: String,
    bgColorStart: Color,
    bgColorEnd: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    val brush = Brush.horizontalGradient(listOf(bgColorStart, bgColorEnd))

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}
