package com.example.socialmediaf.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun ChatScreen(
    conversationId: Int?
) {

    Column {

        Text(
            text = "Conversation ID"
        )

        Text(
            text = "$conversationId"
        )
    }
}