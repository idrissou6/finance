package com.idris.MyFinance.ui.theme.screens.LoginScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.idris.MyFinance.navigation.ROUTE_HOME
import com.idris.MyFinance.navigation.ROUTE_SIGNUP

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Handle login
    fun handleLogin() {
        // Check if the email is valid
        if (!email.contains("@")) {
            errorMessage = "Please enter a valid email"
            return
        }

        // Check if the password is not empty
        if (password.isEmpty()) {
            errorMessage = "Please enter your password"
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Successfully logged in
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(0) { inclusive = true }
                    }
                } else {
                    // Display login error
                    errorMessage = task.exception?.localizedMessage ?: "Login failed. Please try again."
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Login", fontSize = 24.sp)

            // Email TextField
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (email.isEmpty()) {
                            Text("Enter your email", fontSize = 18.sp, color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            // Password TextField
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (password.isEmpty()) {
                            Text("Enter your password", fontSize = 18.sp, color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            // Login Button
            Button(
                onClick = { handleLogin() },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Login", color = Color.White)
            }

            // Error Message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Signup Link
            TextButton(onClick = { navController.navigate(ROUTE_SIGNUP) }) {
                Text("Don't have an account? Sign up")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}
