package com.example.socialmediaf.posts


data class PostRequest(
    var title:String,
    var content:String,
    var category:String,


)

data class PostResponse(
    var message:String,
    var data:PostData,
)

data class PostData(
    var id:Int,
    var user:String,
    var content:String,
    var title:String,
    var category:String,
    var created_at:String,
    var updated_at: String
)