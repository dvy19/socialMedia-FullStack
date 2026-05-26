package com.example.socialmediaf.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RegisterApiInterface {

    @POST("api/accounts/register/")
    suspend fun registerUser(
        @Body request: SignupRequest
    ): Response<SignupResponse>


    @POST("api/accounts/login/")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponse>


}