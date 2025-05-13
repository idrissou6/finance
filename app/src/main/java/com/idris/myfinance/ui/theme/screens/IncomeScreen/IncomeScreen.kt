package com.idris.MyFinance.ui.theme.screens.IncomeScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.idris.MyFinance.navigation.ROUTE_HOME
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun IncomeScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val context = LocalContext.current
    val db = Firebase.firestore
    val auth = Firebase.auth
    val user = auth.currentUser
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = dateFormat.format(Date())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0xFF7F00FF), Color(0xFF00BFFF))
                )
            )
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = "Add Income",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Income Source / Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(12.dp)),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(12.dp)),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = {
                    if (name.isBlank() || amount.isBlank()) {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val parsedAmount = amount.toDoubleOrNull()
                    if (parsedAmount == null || parsedAmount <= 0) {
                        Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (user != null) {
                        val income = hashMapOf(
                            "name" to name.trim(),
                            "amount" to parsedAmount.toString(),
                            "category" to "Income",
                            "date" to currentDate,
                            "uid" to user.uid
                        )

                        db.collection("income")
                            .add(income)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Income added successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate(ROUTE_HOME)
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed to add income", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
            ) {
                Text("Save Income", fontSize = 16.sp, color = Color.White)
            }

            OutlinedButton(
                onClick = { navController.navigate(ROUTE_HOME) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Cancel", fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIncomeScreen() {
    IncomeScreen(navController = rememberNavController())
}
