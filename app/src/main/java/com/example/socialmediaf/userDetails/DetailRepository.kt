package com.example.socialmediaf.userDetails

import android.util.Log
import com.example.socialmediaf.SocialMediaClient
import com.example.socialmediaf.auth.SessionManager
import retrofit2.Response

class DetailRepository(
    private val sessionManager: SessionManager
) {

    private val apiService= SocialMediaClient.createUserProfile

    suspend fun create_profile(
        request: UserDetailRequest
    ): Response<UserDetailResponse> {

        val token = sessionManager.getAuthToken()

        Log.d("TOKEN_CHECK", token ?: "NULL")

        return apiService.createProfile(
            token = "Bearer $token",
            request = request
        )

        Log.d("FINAL_HEADER", "Bearer $token")
    }


    suspend fun get_profile() : Response<UserData>{

        val token = sessionManager.getAuthToken()

        if (token.isNullOrEmpty()) {
            throw Exception("User not authenticated")
        }

        return apiService.getProfile(
            token = "Bearer $token"
        )
    }
    }

