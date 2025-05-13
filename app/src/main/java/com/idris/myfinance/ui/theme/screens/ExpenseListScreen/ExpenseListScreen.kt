package com.idris.MyFinance.ui.theme.screens.ExpenseListScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.idris.MyFinance.R
import com.idris.MyFinance.navigation.ROUTE_ADD_EXPENSE
import com.idris.MyFinance.navigation.ROUTE_HOME

data class Expense(
    val name: String = "",
    val amount: String = "",
    val date: String = "",
    val category: String = "",
    val id: String = "",
    val uid: String = ""
)

@Composable
fun ExpenseListScreen(navController: NavHostController) {
    val expenses = remember { mutableStateListOf<Expense>() }
    val db = Firebase.firestore
    val uid = Firebase.auth.currentUser?.uid

    // Handle case when user is not logged in
    if (uid == null) {
        Toast.makeText(navController.context, "User not logged in", Toast.LENGTH_SHORT).show()
        navController.navigate(ROUTE_HOME) // Redirect to home if not logged in
        return
    }

    LaunchedEffect(Unit) {
        // Firebase query to fetch expenses
        db.collection("expenses")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { result ->
                expenses.clear()
                for (document in result) {
                    val expense = document.toObject(Expense::class.java)
                    // Add valid expenses to the list
                    if (expense.category.lowercase() != "income") {
                        expenses.add(expense)
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure in fetching data
                Toast.makeText(navController.context, "Error fetching data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFEDE7F6), Color(0xFFF3E5F5)) // Light pastel gradient
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp) // Spacing at the top
        ) {
            HeaderSection()

            // Display list of expenses
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(expenses) { expense ->
                    ExpenseCard(expense)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button with icon and text
            ExtendedFloatingActionButton(
                text = { Text("Add Expense") },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                onClick = { navController.navigate(ROUTE_ADD_EXPENSE) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            BackToHomeButton(navController)
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Your Expenses",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1E88E5)
        )
    }
}

@Composable
fun ExpenseCard(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(16.dp)) // Elevated shadow effect
            .clip(RoundedCornerShape(16.dp)), // Rounded corners
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp), // Subtle elevation
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(getCategoryColor(expense.category))
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Used for: ${expense.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text("Category: ${expense.category}", color = Color.Gray, fontSize = 14.sp)
                Text("Amount: KES ${expense.amount}", color = MaterialTheme.colorScheme.secondary, fontSize = 14.sp)
                Text("Date: ${expense.date}", color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun getCategoryColor(category: String): Color {
    return when (category.lowercase()) {
        "food" -> Color(0xFFFF7043) // Food category color
        "transport" -> Color(0xFF29B6F6) // Transport category color
        "utilities" -> Color(0xFFAB47BC) // Utilities category color
        "income" -> Color(0xFF66BB6A) // Income category color
        else -> Color.LightGray // Default category color
    }
}

@Composable
fun BackToHomeButton(navController: NavHostController) {
    IconButton(
        onClick = { navController.navigate(ROUTE_HOME) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF1E88E5), shape = RoundedCornerShape(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Back to Home", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExpenseListScreen() {
    ExpenseListScreen(navController = rememberNavController())
}
