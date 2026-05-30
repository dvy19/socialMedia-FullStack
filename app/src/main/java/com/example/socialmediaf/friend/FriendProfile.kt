package com.example.socialmediaf.friend


import ProfileScreenLayout
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.navigation.NavController
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmediaf.SessionManager
import com.example.socialmediaf.userDetails.DetailRepository
import com.example.socialmediaf.userDetails.DetailViewModelFactory
import com.example.socialmediaf.userDetails.UserDetailViewModel
import androidx.compose.runtime.collectAsState
import com.example.socialmediaf.userDetails.UserDetailState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PersonAdd
import com.example.socialmediaf.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendProfile(
    mainNavController: NavController,
    userId: Int?
) {

    val accentColor = Color(0xFFBB86FC)
    val surfaceColor = Color(0xFF1E1E1E)

    val context=LocalContext.current

    val sessionManager=SessionManager(context)

    val repository= FriendRepository(sessionManager)

    val viewModel: FriendViewModel=viewModel()

    val friendState = viewModel.friendDetailState.collectAsState()



    LaunchedEffect(Unit) {

        userId?.let {

            viewModel.getUserProfile(
                it
            )
        }
    }




    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Your Profile")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                },

                actions = {

                    IconButton(
                        onClick = {
                            // mainNavController.navigate(Screens.Suggestion.route)


                        }
                    ) {
                        Icon(
                            Icons.Default.Message,
                            contentDescription = null
                        )
                    }
                }
            )
        },

        floatingActionButton = {

            FloatingActionButton(
                onClick = {

                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null
                )
            }
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            when (val result = friendState) {

                is FriendProfileState.Idle -> {

                    Text("Loading...")
                }

                is FriendProfileState.Loading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is FriendProfileState.Success -> {

                    val profile = result.userData

                    ProfileScreenLayout(

                        onLogout = {
                            sessionManager.logout()

                            mainNavController.navigate("login")
                        },

                        onActivity = {

                        },

                        first_name = profile.first_name,
                        bio = profile.bio
                    )
                }

                is UserDetailState.Error -> {

                    Text("Error")
                }
            }
        }
    }
}



