package com.example.socialmediaf.posts


data class PostRequest(
    var title:String,
    var content:String,
    var category:String,


)

data class PostResponse(
    var id:Int,
    var user:PostUser,
    var title:String,
    var content:String,
    var category:String,
    var created_at:String,
    var updated_at:String
)

data class PostUser(
    var id:Int,
    var email:String,
    var first_name:String?,
    var last_name:String?,
    var city:String?

)



/*
 {
        "id": 2,
        "user": {
            "id": 12,
            "email": "archita44@gmail.com",
            "first_name": null,
            "last_name": null,
            "city": null
        },
        "title": "first post",
        "content": "my first post as here",
        "category": "fashion",
        "created_at": "2026-05-26T19:21:04.344531Z",
        "updated_at": "2026-05-26T19:21:04.344556Z"
    },
 */