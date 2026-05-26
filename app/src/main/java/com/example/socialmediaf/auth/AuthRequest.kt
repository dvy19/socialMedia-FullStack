package com.example.socialmediaf.auth

data class SignupRequest(
    var email: String,
    var password:String,
    var role: String
)

data class SignupResponse(

    var message:String,
    var role:String,
    var tokens:Token

)

data class Token(
    var refresh:String,
    var access:String
)

data class LoginRequest(
    var email:String,
    var password:String

)

data class LoginResponse(
    var message:String,
    var role:String,
    var tokens:Token
)


