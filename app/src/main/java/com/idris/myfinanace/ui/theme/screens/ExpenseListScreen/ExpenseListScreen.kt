package com.idris.myfinanace.ui.theme.screens.ExpenseListScreen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.idris.myfinanace.navigation.ROUTE_HOME
import com.idris.myfinanace.ui.theme.Screens.HomeScreen.HomeScreen

@Composable
fun ExpenseListScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Expense List")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.navigate(ROUTE_HOME) }) {
            Text(text = "Back to Home")
        }
    }
}
@Preview()
@Composable
fun PreviewExpenseListScreen() {
    ExpenseListScreen(rememberNavController())
}

