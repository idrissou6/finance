package com.idris.MyFinance.ui.theme.screens.SignUpScreen

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
import com.idris.MyFinance.navigation.ROUTE_LOGIN

@Composable
fun SignUpScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Handle signup
    fun handleSignUp() {
        // Check if the email is a Gmail address
        if (!email.endsWith("@gmail.com")) {
            errorMessage = "Please use a valid Gmail address"
            return
        }

        // Check if the password and confirm password match
        if (password != confirmPassword) {
            errorMessage = "Passwords do not match"
            return
        }

        // Check if password is strong enough
        if (password.length < 6) {
            errorMessage = "Password should be at least 6 characters"
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                // Email verification sent successfully
                                Toast.makeText(context, "Verification email sent", Toast.LENGTH_SHORT).show()
                                navController.navigate(ROUTE_LOGIN)
                            } else {
                                errorMessage = "Failed to send verification email. Please try again."
                            }
                        }
                } else {
                    // Display signup error
                    errorMessage = task.exception?.localizedMessage ?: "Signup failed. Please try again."
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
            Text(text = "Sign Up", fontSize = 24.sp)

            // Email TextField
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (email.isEmpty()) {
                            Text("Enter your Gmail", fontSize = 18.sp, color = Color.Gray)
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

            // Confirm Password TextField
            BasicTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (confirmPassword.isEmpty()) {
                            Text("Confirm your password", fontSize = 18.sp, color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            // Sign Up Button
            Button(
                onClick = { handleSignUp() },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Sign Up", color = Color.White)
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

            // Login Link
            TextButton(onClick = { navController.navigate(ROUTE_LOGIN) }) {
                Text("Already have an account? Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(navController = rememberNavController())
}
