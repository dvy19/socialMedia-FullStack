package com.example.socialmediaf.chat

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ChatApiInterface {

    @POST("api/chat/create-conversation/")
    suspend fun createConversation(
        @Header("Authorization")
        token: String,

        @Body
        request: CreateConversationRequest
    ): Response<CreateConversationResponse>

}
