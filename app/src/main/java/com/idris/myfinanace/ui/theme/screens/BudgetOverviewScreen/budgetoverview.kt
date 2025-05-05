package com.idris.myfinanace.ui.theme.screens.BudgetOverviewScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.idris.myfinanace.navigation.ROUTE_HOME

// Sample data
data class Budget(val totalIncome: String, val totalExpenses: String, val remainingBalance: String)

val sampleBudget = Budget("$5000", "$1200", "$3800")

@Composable
fun BudgetOverviewScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Budget Overview",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        BudgetCard(title = "Total Income", value = sampleBudget.totalIncome, color = Color.Green)
        BudgetCard(title = "Total Expenses", value = sampleBudget.totalExpenses, color = Color.Red)
        BudgetCard(title = "Remaining Balance", value = sampleBudget.remainingBalance, color = Color.Blue)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate(ROUTE_HOME) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Back to Home", color = Color.White)
        }
    }
}

@Composable
fun BudgetCard(title: String, value: String, color: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyMedium)
            Text(text = value, style = MaterialTheme.typography.titleLarge, color = color)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBudgetOverviewScreen() {
    BudgetOverviewScreen(rememberNavController())
}
