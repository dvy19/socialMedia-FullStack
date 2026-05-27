package com.example.socialmediaf.userScreens


import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.rememberNavController

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.socialmediaf.BottomNavBar
import com.example.socialmediaf.Screens

@Composable
fun MainScreen(rootNavController: NavController) {

    val mainNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(mainNavController)
        }
    ) { paddingValues ->

        NavHost(
            navController = mainNavController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screens.Home.route) {
                HomeScreen(mainNavController)
            }

        }
    }
}