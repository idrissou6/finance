package com.idris.MyFinance.ui.theme.screens.LoginScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.idris.MyFinance.R
import com.idris.MyFinance.navigation.ROUTE_HOME
import com.idris.MyFinance.navigation.ROUTE_SIGNUP

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current


    fun handleLogin() {

        if (!email.contains("@")) {
            errorMessage = "Please enter a valid email"
            return
        }


        if (password.isEmpty()) {
            errorMessage = "Please enter your password"
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    navController.navigate(ROUTE_HOME) {
                        popUpTo(0) { inclusive = true }
                    }
                } else {

                    errorMessage = task.exception?.localizedMessage ?: "Login failed. Please try again."
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
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 16.dp)
            )

            Text(text = "Login", fontSize = 24.sp)


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


            Button(
                onClick = { handleLogin() },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Login", color = Color.White)
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
