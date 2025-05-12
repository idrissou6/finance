package com.idris.MyFinance.ui.theme.screens.BudgetOverviewScreen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.idris.MyFinance.navigation.ROUTE_EXPENSE_LIST
import com.idris.MyFinance.ui.theme.screens.ExpenseListScreen.Expense

@Composable
fun BudgetOverviewScreen(navController: NavHostController) {
    val db = Firebase.firestore
    val auth = Firebase.auth
    val user = auth.currentUser

    val expenses = remember { mutableStateListOf<Expense>() }
    var totalIncome by remember { mutableStateOf(0.0) }
    var totalExpenses by remember { mutableStateOf(0.0) }

    val context = LocalContext.current

    LaunchedEffect(user?.uid) {
        user?.uid?.let { uid ->
            db.collection("expenses")
                .whereEqualTo("uid", uid)
                .addSnapshotListener { snapshot, error ->
                    if (snapshot != null && !snapshot.isEmpty) {
                        expenses.clear()
                        for (doc in snapshot.documents) {
                            val expense = doc.toObject(Expense::class.java)
                            expense?.let { expenses.add(it) }
                        }

                        totalIncome = expenses.filter { it.category.lowercase() == "income" }
                            .sumOf { it.amount.toDoubleOrNull() ?: 0.0 }

                        totalExpenses = expenses.filter { it.category.lowercase() != "income" }
                            .sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
                    }
                }
        }
    }

    val remainingBalance = totalIncome - totalExpenses
    val progress = if (totalIncome > 0) (totalExpenses / totalIncome).toFloat() else 0f


    LaunchedEffect(progress) {
        if (progress > 0.8f) {
            showBudgetNotification(
                context,
                "Budget Alert",
                "You’ve spent over 80% of your income!"
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFAFAFA), Color(0xFFEDE7F6)) // Login-style gradient
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Budget Overview",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A148C)
            )

            ProgressIndicator(progress)

            BudgetPieChartCanvas(income = totalIncome, expenses = totalExpenses)

            Text(
                text = "Remaining Balance: KES ${"%.2f".format(remainingBalance)}",
                fontSize = 20.sp,
                color = Color(0xFF4A148C)
            )

            Button(
                onClick = {
                    navController.navigate(ROUTE_EXPENSE_LIST)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Text("Go to Expense Details", color = Color.White)
            }
        }
    }
}

@Composable
fun ProgressIndicator(progress: Float) {
    LinearProgressIndicator(
        progress = progress.coerceIn(0f, 1f),
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
            .clip(RoundedCornerShape(6.dp)),
        color = Color(0xFF4CAF50),
        trackColor = Color(0xFFE0E0E0)
    )
}

@Composable
fun BudgetPieChartCanvas(income: Double, expenses: Double) {
    val total = income + expenses
    val incomeAngle = if (total > 0) (income / total * 360).toFloat() else 0f
    val expensesAngle = 360f - incomeAngle

    val incomeColor = Color(0xFF4CAF50)
    val expenseColor = Color(0xFFF44336)

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(200.dp)) {
            var startAngle = -90f

            drawArc(
                color = incomeColor,
                startAngle = startAngle,
                sweepAngle = incomeAngle,
                useCenter = true
            )

            startAngle += incomeAngle

            drawArc(
                color = expenseColor,
                startAngle = startAngle,
                sweepAngle = expensesAngle,
                useCenter = true
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Income: KES ${"%.0f".format(income)}", color = incomeColor, fontSize = 16.sp)
            Text("Expenses: KES ${"%.0f".format(expenses)}", color = expenseColor, fontSize = 16.sp)
        }
    }
}

fun showBudgetNotification(context: Context, title: String, message: String) {
    val channelId = "budget_channel"
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Budget Alerts",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_alert)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    notificationManager.notify(1, builder.build())
}

@Preview(showBackground = true)
@Composable
fun PreviewBudgetOverview() {
    BudgetOverviewScreen(navController = rememberNavController())
}
