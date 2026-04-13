package com.upb.shoplist

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Recover : Screen("recover")
    object Home : Screen("home")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onRegisterClick = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Splash.route) { inclusive = false }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = false }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                context = context,
                onBackClick = { navController.popBackStack() },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {
                    navController.navigate(Screen.Recover.route)
                },
                onLoginSuccess = { userName ->
                    val sharedPref = context.getSharedPreferences("SessionData", Context.MODE_PRIVATE)
                    sharedPref.edit().apply {
                        putBoolean("isLoggedIn", true)
                        putString("userName", userName)
                        apply()
                    }
                    val intent = Intent(context, HomeActivityCompose::class.java)
                    intent.putExtra("USER_NAME", userName)
                    context.startActivity(intent)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                context = context,
                onBackClick = { navController.popBackStack() },
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Recover.route) {
            RecoverPasswordScreen(
                context = context,
                onBackClick = { navController.popBackStack() },
                onComplete = { navController.popBackStack() }
            )
        }
    }
}