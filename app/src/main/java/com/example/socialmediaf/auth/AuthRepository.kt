package com.example.socialmediaf.auth


class AuthRepository{

    suspend fun registerUser(request: SignupRequest): Result<SignupResponse> {
        return try {
            val response = JobPortalApiClient.registerApi.registerUser(request)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


/*
    suspend fun loginUser(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = JobPortalApiClient.registerApi.loginUser(request)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

 */


}