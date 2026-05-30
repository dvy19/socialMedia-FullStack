package com.example.socialmediaf.friend

import com.example.socialmediaf.SessionManager
import com.example.socialmediaf.SocialMediaClient
import com.example.socialmediaf.userDetails.UserData
import retrofit2.Response

class FriendRepository(
    private val sessionManager: SessionManager

) {

    val api= SocialMediaClient.getFriendProfile


    suspend fun getUserProfile(

        id: Int
    ): Response<UserData> {

        val token=sessionManager.getAuthToken()

        if(token.isNullOrEmpty()){
            throw Exception("User not authenticated")
        }

        return api.getUserProfile(token, id)
    }
}