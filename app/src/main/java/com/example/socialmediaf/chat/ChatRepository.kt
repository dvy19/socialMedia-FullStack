package com.example.socialmediaf.chat

import com.example.socialmediaf.SessionManager
import com.example.socialmediaf.SocialMediaClient
import retrofit2.Response


class ChatRepository (
    private val sessionManager: SessionManager
){

    val token=sessionManager.getAuthToken()

    val api= SocialMediaClient.chatApiService

    suspend fun createConversation(
        token: String,
        profileId: Int
    ): Response<CreateConversationResponse> {

        return api.createConversation(
            token = token,
            request = CreateConversationRequest(
                profileId = profileId
            )
        )
    }
}