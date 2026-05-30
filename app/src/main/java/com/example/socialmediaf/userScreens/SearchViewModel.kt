package com.example.socialmediaf.userScreens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.example.socialmediaf.SocialMediaClient
import com.example.socialmediaf.userDetails.UserData

class SearchViewModel : ViewModel() {

    var users by mutableStateOf<List<UserData>>(emptyList())
        private set

    fun searchUsers(query: String) {

        viewModelScope.launch {

            try {

                val response =
                    SocialMediaClient.searchUserApi.searchUsers(

                        query)

                if (response.isSuccessful) {

                    Log.d("m", users.toString())

                    users = response.body() ?: emptyList()
                }

                Log.d("m",response.toString())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}