package com.example.socialmediaf.userScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.ui.unit.sp
import com.example.socialmediaf.userDetails.UserData
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    mainNavController: NavController
) {

    val viewModel: SearchViewModel = viewModel()

    var searchText by remember {
        mutableStateOf("")
    }

    val users = viewModel.users

    /*
        Debounce Effect
     */
    LaunchedEffect(searchText) {

        delay(500)

        if (searchText.isNotEmpty()) {
            viewModel.searchUsers(searchText)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            label = {
                Text("Search Users")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(users) { user ->

                UserCard(
                    user,
                    onProfileClick = {
                        mainNavController.navigate(
                            "friend_profile/${user.id}"
                        )
                    }
                )

            }
        }
    }
}

@Composable
fun UserCard(
    user: UserData,
    onProfileClick: () -> Unit = {}

) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable{
                onProfileClick()
            },
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = user.first_name,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = user.bio)
        }
    }
}