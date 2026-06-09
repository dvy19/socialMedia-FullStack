package com.example.socialmediaf.userScreens


import ProfileScreen
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
import com.example.socialmediaf.Screens.CreatePost
import com.example.socialmediaf.friend.FriendProfile
import com.example.socialmediaf.posts.CreatePostScreen

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
                HomeScreen(
                    mainNavController,
                    onAddPostClick = {  mainNavController.navigate(Screens.CreatePost.route)},
                    onNotificationClick = { },
                    onMessageClick = {  }
                )
            }

            composable(Screens.ProfileScreen.route){
                ProfileScreen(
                    mainNavController,
                    rootNavController

                )
            }

            composable(Screens.CreatePost.route){
                CreatePostScreen(mainNavController)
            }

            composable(Screens.SearchScreen.route){
                SearchScreen(mainNavController)
            }

            composable(Screens.FriendProfile.route){
                    backStackEntry ->

                val id =
                    backStackEntry.arguments
                        ?.getString("id")
                        ?.toIntOrNull()

                FriendProfile(
                    userId = id,
                    mainNavController=mainNavController
                )
            }

        }
    }
}