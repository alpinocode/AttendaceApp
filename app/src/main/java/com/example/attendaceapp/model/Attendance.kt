package com.example.attendaceapp.model

data class AttendanceUser(
    val id:Int?= null,
    val date:String? = null,
    val dateMonth:String? = null,
    val imageCheckIn:String? = null,
    val locationCheckIn:String? = null,
    val dateHoursCheckIn: String? = null,
    val imageCheckOut:String? =null,
    val locationCheckOut:String? = null,
    val dateHoursCheckOut:String? = null,
    val status:String?= null
)

