package com.idris.myfinanace.ui.theme.screens.ExpenseListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.idris.myfinanace.navigation.ROUTE_HOME
import com.idris.myfinance.R

// Sample data class and dummy data
data class Expense(val name: String, val amount: String, val date: String, val category: String)

val sampleExpenses = listOf(
    Expense("Groceries", "$120", "05/01/2025", "Food"),
    Expense("Transport", "$15", "05/02/2025", "Travel"),
    Expense("Subscription", "$9.99", "05/03/2025", "Entertainment"),
)

@Composable
fun ExpenseListScreen(navController: NavHostController) {

    val backgroundImage = painterResource(id = R.drawable.money) // Ensure this image exists in your drawable folder

    Box(modifier = Modifier.fillMaxSize()) {

        // Background Image
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )

        // Content Overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header Text
            Text(
                text = "Your Expenses",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Expense List
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sampleExpenses) { expense ->
                    ExpenseCard(expense = expense)
                }
            }

            // Back Button
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(ROUTE_HOME) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Back to Home", color = Color.White)
            }
        }
    }
}

@Composable
fun ExpenseCard(expense: Expense) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = expense.name, style = MaterialTheme.typography.titleMedium)
                Text(text = expense.category, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Amount: ${expense.amount}", color = MaterialTheme.colorScheme.secondary)
            Text(text = "Date: ${expense.date}", color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExpenseListScreen() {
    ExpenseListScreen(rememberNavController())
}
