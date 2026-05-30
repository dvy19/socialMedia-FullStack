package com.example.socialmediaf.friend

import com.example.socialmediaf.userDetails.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FriendApiInterface{

    @GET("api/accounts/profile/{id}/")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<UserData>
}