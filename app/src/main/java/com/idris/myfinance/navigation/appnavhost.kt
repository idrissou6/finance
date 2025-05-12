package com.idris.MyFinance.navigation



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.idris.MyFinance.ui.theme.screens.HomeScreen.HomeScreen
import com.idris.MyFinance.ui.theme.screens.AboutScreen.AboutScreen
import com.idris.MyFinance.ui.theme.screens.AddExpenseScreen.AddExpenseScreen
import com.idris.MyFinance.ui.theme.screens.BudgetOverviewScreen.BudgetOverviewScreen
import com.idris.MyFinance.ui.theme.screens.EditProfileScreen.EditProfileScreen
import com.idris.MyFinance.ui.theme.screens.ExpenseListScreen.ExpenseListScreen
import com.idris.MyFinance.ui.theme.screens.IncomeScreen.IncomeScreen
import com.idris.MyFinance.ui.theme.screens.LoginScreen.LoginScreen
import com.idris.MyFinance.ui.theme.screens.SignUpScreen.SignUpScreen
import com.idris.MyFinance.ui.theme.screens.SplashScreen.SplashScreen
import com.idris.MyFinance.ui.theme.screens.UserProfileScreen.UserProfileScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        composable(ROUTE_SPLASH) {
            SplashScreen(navController)
        }
        composable(ROUTE_ADD_EXPENSE) {
            AddExpenseScreen(navController)
        }
        composable(ROUTE_EXPENSE_LIST) {
            ExpenseListScreen(navController)
        }
        composable(ROUTE_ABOUT) {
            AboutScreen(navController)
        }
        composable(ROUTE_BUDGET_OVERVIEW) {
            BudgetOverviewScreen(navController)
        }
        composable(ROUTE_HOME) {
            HomeScreen(navController)
        }
        composable(ROUTE_INCOME) {
            IncomeScreen(navController)
        }
        composable(ROUTE_LOGIN) {
            LoginScreen(navController)
        }
        composable(ROUTE_SIGNUP) {
            SignUpScreen(navController)
        }
        composable(ROUTE_USERPROFILE) {
            UserProfileScreen(navController)
        }
        composable(ROUTE_EDIT_PROFILE) {
            EditProfileScreen(navController)
        }

    }
}


