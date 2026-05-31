package com.example.socialmediaf

import com.example.socialmediaf.auth.RegisterApiInterface
import com.example.socialmediaf.chat.ChatApiInterface
import com.example.socialmediaf.friend.FriendApiInterface
import com.example.socialmediaf.posts.PostApiInterface
import com.example.socialmediaf.userDetails.UserDetailInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object SocialMediaClient {

    private const val BASE_URL = "https://social-media-backend-twdg.onrender.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ✅ Register API
    val registerApi: RegisterApiInterface by lazy {
        retrofit.create(RegisterApiInterface::class.java)
    }

    val getPostsApi: PostApiInterface by lazy{
        retrofit.create(PostApiInterface::class.java)
    }

    val createUserProfile: UserDetailInterface by lazy{
        retrofit.create(UserDetailInterface::class.java)
    }

    val searchUserApi: UserDetailInterface by lazy{
        retrofit.create(UserDetailInterface::class.java)
    }

    val getFriendProfile: FriendApiInterface by lazy{
        retrofit.create(FriendApiInterface::class.java)
    }

    val chatApiService: ChatApiInterface by lazy{
        retrofit.create(ChatApiInterface::class.java)
    }
}