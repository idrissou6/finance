package com.idris.MyFinance.ui.theme.screens.EditProfileScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.idris.MyFinance.navigation.ROUTE_USERPROFILE


@Composable
fun EditProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val db = FirebaseFirestore.getInstance()

    var displayName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    // Load existing displayName from Firestore
    LaunchedEffect(user) {
        user?.uid?.let { uid ->
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    displayName = document.getString("displayName") ?: (user.displayName ?: "")
                    isLoading = false
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = displayName,
            onValueChange = { displayName = it },
            label = { Text("Display Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Save Changes Button
        Button(
            onClick = {
                user?.uid?.let { uid ->
                    db.collection("users").document(uid)
                        .update("displayName", displayName)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                            navController.navigate(ROUTE_USERPROFILE) {
                                popUpTo(ROUTE_USERPROFILE) { inclusive = true }
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                        }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Save Changes", color = MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cancel/Back Button
        Button(
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Cancel", color = MaterialTheme.colorScheme.onSecondary, fontSize = 16.sp)
        }
    }
}
