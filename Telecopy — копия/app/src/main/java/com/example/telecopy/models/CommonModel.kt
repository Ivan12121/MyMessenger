package com.example.telecopy.models

data class CommonModel(
    val id:String = "",
    var username: String = "",
    var info: String = "",
    var fullname: String = "",
    var state: String = "",
    var photoUrl: String = "empty",
    var phone:String = "",

    val text: String = "",
    var type: String = "",
    var from: String = "",
    var timeStamp: Any = ""

)