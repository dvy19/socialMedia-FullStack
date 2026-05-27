package com.example.socialmediaf.posts

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface PostApiInterface{

    @GET("api/posts/create-post")
    suspend fun getAllPosts(
        @Header ("Authorization") token:String,
    ) : Response<List<PostData>>
}