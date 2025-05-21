package com.example.attendaceapp.model

data class UserAuth(
    val id:String?= null,
    val name:String? =null,
    val email:String? = null,
    var role:String? = null,
    var bio:String?= null,
    var image:String? =null
)