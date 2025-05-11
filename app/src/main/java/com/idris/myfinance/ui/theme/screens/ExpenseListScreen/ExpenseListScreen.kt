package com.idris.MyFinance.ui.theme.screens.ExpenseListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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

    LaunchedEffect(Unit) {
        if (uid != null) {
            db.collection("expenses")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener { result ->
                    expenses.clear()
                    for (document in result) {
                        val expense = document.toObject(Expense::class.java)
                        if (expense.category.lowercase() != "income") {
                            expenses.add(expense)
                        }
                    }
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF512DA8))  // Updated background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HeaderSection()

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(expenses) { expense ->
                    ExpenseCard(expense)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(Modifier.padding(16.dp)) {
            Box(
                Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(getCategoryColor(expense.category))
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text("Used for: ${expense.name}", fontWeight = FontWeight.Bold)
                Text("Category: ${expense.category}", color = Color.Gray)
                Text("Amount: ${expense.amount}", color = MaterialTheme.colorScheme.secondary)
                Text("Date: ${expense.date}", color = Color.Gray)
            }
        }
    }
}

@Composable
fun getCategoryColor(category: String): Color {
    return when (category.lowercase()) {
        "food" -> Color(0xFFFF7043)
        "transport" -> Color(0xFF29B6F6)
        "utilities" -> Color(0xFFAB47BC)
        "income" -> Color(0xFF66BB6A)
        else -> Color.LightGray
    }
}

@Composable
fun BackToHomeButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate(ROUTE_HOME) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = "Back to Home", color = Color.White, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExpenseListScreen() {
    ExpenseListScreen(navController = rememberNavController())
}
