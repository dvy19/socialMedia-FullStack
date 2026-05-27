package com.example.socialmediaf.posts

import com.example.socialmediaf.auth.SessionManager
import com.example.socialmediaf.SocialMediaClient
import retrofit2.Response

class PostRepository(
    private val sessionManager: SessionManager,

){

    private val apiService= SocialMediaClient.getPostsApi

    suspend fun get_all_posts(token: String):Response<List<PostData>>{

        return apiService.getAllPosts(
            token=token
        )
    }



}