package com.example.socialmediaf

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.socialmediaf.Screens.SignupScreen

import com.example.socialmediaf.auth.LoginScreen
import com.example.socialmediaf.auth.SignupScreen
import com.example.socialmediaf.userDetails.UserDetailScreen
import com.example.socialmediaf.userScreens.MainScreen


@Composable
fun RootNav(innerPadding: PaddingValues) {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = "signup"
    ) {

        composable("splash"){
            SplashScreen(rootNavController)
        }

        composable(Screens.LoginScreen.route) {
            LoginScreen(rootNavController)
        }

        composable("signup") {
            SignupScreen(rootNavController)
        }

        composable(Screens.MainScreen.route){
            MainScreen(rootNavController)
        }

        composable(Screens.UserDetailScreen.route){
            UserDetailScreen(rootNavController)
        }



    }
}