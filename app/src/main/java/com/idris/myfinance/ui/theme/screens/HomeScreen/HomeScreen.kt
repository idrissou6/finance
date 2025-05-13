package com.idris.MyFinance.ui.theme.screens.HomeScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.idris.MyFinance.navigation.*

@Composable
fun HomeScreen(navController: NavHostController) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF121212)) // Simple dark background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MyFinance",
                color = Color.White,
                fontSize = 34.sp
            )

            Text(
                text = "Track your expenses efficiently.",
                color = Color.LightGray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            GlassBox {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconColumnItem("Add Income", Icons.Default.MonetizationOn, Color(0xFF03A9F4)) {
                            navController.navigate(ROUTE_INCOME)
                        }
                        IconColumnItem("Add Expense", Icons.Default.AddCircle, Color(0xFF1EB980)) {
                            navController.navigate(ROUTE_ADD_EXPENSE)
                        }
                        IconColumnItem("Expenses", Icons.Default.List, Color(0xFF8BC34A)) {
                            navController.navigate(ROUTE_EXPENSE_LIST)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconColumnItem("Overview", Icons.Default.BarChart, Color(0xFF3700B3)) {
                            navController.navigate(ROUTE_BUDGET_OVERVIEW)
                        }
                        IconColumnItem("About", Icons.Default.Info, Color.DarkGray) {
                            navController.navigate(ROUTE_ABOUT)
                        }
                    }
                }
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

        NavigationBar(
            containerColor = Color(0xFF3700B3),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            NavigationBarItem(
                selected = selectedTab == 0,
                onClick = {
                    selectedTab = 0
                    navController.navigate(ROUTE_HOME)
                },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") },
                alwaysShowLabel = true
            )
            NavigationBarItem(
                selected = selectedTab == 1,
                onClick = {
                    selectedTab = 1
                    navController.navigate(ROUTE_USERPROFILE)
                },
                icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                label = { Text("Profile") },
                alwaysShowLabel = true
            )
            NavigationBarItem(
                selected = selectedTab == 2,
                onClick = {
                    selectedTab = 2
                    showLogoutDialog = true
                },
                icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Logout") },
                label = { Text("Logout") },
                alwaysShowLabel = true
            )
        }
    }
}

@Composable
fun IconColumnItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    bgColor: Color,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.95f else 1f, label = "scale")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    pressed = true
                    onClick()
                    pressed = false
                }
            )
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(color = bgColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(30.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun GlassBox(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.08f), shape = RoundedCornerShape(20.dp))
            .padding(20.dp),
        content = content
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}
