package com.idris.MyFinance.ui.theme.screens.SignUpScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

    fun handleSignUp() {
        if (!email.endsWith("@gmail.com")) {
            errorMessage = "Please use a valid Gmail address"
            return
        }

        if (password != confirmPassword) {
            errorMessage = "Passwords do not match"
            return
        }

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
                                Toast.makeText(context, "Verification email sent", Toast.LENGTH_SHORT).show()
                                navController.navigate(ROUTE_LOGIN)
                            } else {
                                errorMessage = "Failed to send verification email. Please try again."
                            }
                        }
                } else {
                    errorMessage = task.exception?.localizedMessage ?: "Signup failed. Please try again."
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF512DA8))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Sign Up", fontSize = 24.sp, color = Color.White)

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { /* focus to next field */ }
                ),
                isError = errorMessage.isNotEmpty()
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { /* focus to next field */ }
                ),
                visualTransformation = PasswordVisualTransformation(),
                isError = errorMessage.isNotEmpty()
            )

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { handleSignUp() }
                ),
                visualTransformation = PasswordVisualTransformation(),
                isError = errorMessage.isNotEmpty()
            )

            Button(
                onClick = { handleSignUp() },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Sign Up", color = Color.White)
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate(ROUTE_LOGIN) }) {
                Text("Already have an account? Login", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(navController = rememberNavController())
}
