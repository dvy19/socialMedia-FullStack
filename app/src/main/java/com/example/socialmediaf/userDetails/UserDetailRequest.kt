package com.example.socialmediaf.userDetails

data class UserDetailRequest(

    var bio:String,
    var city:String,
    var date_of_birth:String,
    var first_name:String,
    var last_name:String,
    var gender:String,

)

data class UserDetailResponse(
    var message :String,
    var data:UserData
)

data class UserData(
    var id:Int,
    var first_name:String,
    var last_name:String,
    var city:String,
    var gender:String,
    var date_of_birth:String,
    var bio:String,
    var user:String,
    var created_at:String,
    var updated_at:String,
    var social_stats:String?
)

/*
{
    "bio": "hello  i am divy",
    "city": "kanpur",
    "date_of_birth": "2000-12-12",
    "first_name": "divya",
    "gender": "male",
    "last_name": "dixit"
}
 */

/*
{
    "message": "Profile created successfully",
    "data": {
        "id": 4,
        "first_name": "divya",
        "last_name": "dixit",
        "city": "kanpur",
        "gender": "male",
        "date_of_birth": "2000-12-12",
        "bio": "hello  i am divy",
        "user": "vaidik01@gmail.com (user)",
        "created_at": "2026-05-27T06:14:39.690620Z",
        "updated_at": "2026-05-27T06:14:39.690635Z",
        "social_stats": null
    }
}
 */


/*
using get,

{
    "id": 4,
    "first_name": "divya",
    "last_name": "dixit",
    "city": "kanpur",
    "gender": "male",
    "date_of_birth": "2000-12-12",
    "bio": "hello  i am divy",
    "user": "vaidik01@gmail.com (user)",
    "created_at": "2026-05-27T06:14:39.690620Z",
    "updated_at": "2026-05-27T06:14:39.690635Z",
    "social_stats": null
}

 */