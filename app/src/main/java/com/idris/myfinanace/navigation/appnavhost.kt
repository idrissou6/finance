package com.idris.myfinanace.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.idris.myfinanace.ui.theme.Screens.HomeScreen.HomeScreen
import com.idris.myfinanace.ui.theme.screens.AddExpenseScreen.AddExpenseScreen
import com.idris.myfinanace.ui.theme.screens.ExpenseListScreen.ExpenseListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_HOME
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        composable(ROUTE_HOME) {
            HomeScreen(navController)
        }
        composable(ROUTE_ADD_EXPENSE) {
            AddExpenseScreen(navController)
        }
        composable(ROUTE_EXPENSE_LIST) {
            ExpenseListScreen(navController)
        }
    }
}


