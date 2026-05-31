package com.example.socialmediaf.chat

import com.google.gson.annotations.SerializedName

data class CreateConversationRequest(
    @SerializedName("profile_id")
    val profileId: Int
)

data class CreateConversationResponse(
    @SerializedName("conversation_id")
    val conversationId: Int
)
