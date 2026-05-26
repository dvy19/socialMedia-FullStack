package com.example.socialmediaf.auth

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object JobPortalApiClient {

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

}