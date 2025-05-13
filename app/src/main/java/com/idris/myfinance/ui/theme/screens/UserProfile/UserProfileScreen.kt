package com.idris.MyFinance.ui.theme.screens.UserProfileScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.idris.MyFinance.R
import com.idris.MyFinance.navigation.ROUTE_LOGIN
import com.idris.MyFinance.navigation.ROUTE_EDIT_PROFILE

@Composable
fun UserProfileScreen(navController: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current

    if (user == null) {
        Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
        navController.navigate(ROUTE_LOGIN) { popUpTo(0) }
        return
    }

    val profilePictureUrl = user.photoUrl?.toString()
    val profilePainter = if (!profilePictureUrl.isNullOrEmpty()) {
        rememberAsyncImagePainter(profilePictureUrl)
    } else {
        painterResource(id = R.drawable.img_1)
    }

    val displayName = user.displayName ?: "No name set"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            Text(
                text = "User Profile",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = profilePainter,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .shadow(10.dp, CircleShape)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Name: $displayName",
                color = Color.LightGray,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Email: ${user.email ?: "Not available"}",
                color = Color.LightGray,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            ProfileActionItem("Edit Profile", Icons.Default.Edit, Color(0xFF03A9F4)) {
                navController.navigate(ROUTE_EDIT_PROFILE)
            }

            Spacer(modifier = Modifier.height(16.dp))

            ProfileActionItem("Change Password", Icons.Default.Password, Color(0xFF43A047)) {
                user.email?.let { email ->
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    Toast.makeText(context, "Reset link sent to $email", Toast.LENGTH_SHORT).show()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ProfileActionItem("Logout", Icons.Default.Logout, Color(0xFFEF5350)) {
                FirebaseAuth.getInstance().signOut()
                navController.navigate(ROUTE_LOGIN) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun ProfileActionItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(containerColor = color),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Icon(icon, contentDescription = title, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, color = Color.White, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfileScreen() {
    UserProfileScreen(navController = rememberNavController())
}
