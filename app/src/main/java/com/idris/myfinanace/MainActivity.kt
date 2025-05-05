package com.idris.myfinanace

//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.idris.myfinanace.navigation.AppNavHost
//import com.idris.myfinanace.ui.theme.MyFinanaceTheme
//import androidx.navigation.compose.rememberNavController
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            MyFinanaceTheme {
//                // Create the navController for handling navigation between screens
//                val navController = rememberNavController()
//
//                // Use Scaffold to provide consistent layout structure
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    // Include the AppNavHost here to manage the screens and navigation
//                    AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyFinanaceTheme {
//        // You can add a preview of your main navigation here
//        // e.g., Previewing the HomeScreen
//        val navController = rememberNavController()
//        AppNavHost(navController = navController)
//    }
//}

//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
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.idris.myfinanace.navigation.AppNavHost
import com.idris.myfinanace.ui.theme.MyFinanaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MyFinanaceTheme {

                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
