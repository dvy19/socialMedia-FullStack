package com.example.socialmediaf.posts

import com.example.socialmediaf.SessionManager
import com.example.socialmediaf.SocialMediaClient
import retrofit2.Response

class PostRepository(
    private val sessionManager: SessionManager,

){

    private val apiService= SocialMediaClient.getPostsApi

    suspend fun get_all_posts():Response<List<PostData>>{

        val token = sessionManager.getAuthToken()
            ?: throw IllegalStateException("User is not authenticated")

        return apiService.getAllPosts(
            token = "Bearer $token"

        )
    }



}