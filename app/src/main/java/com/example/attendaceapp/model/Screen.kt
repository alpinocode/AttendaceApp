package com.example.attendaceapp.model

sealed class Screen(val route:String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
    object Attendance : Screen("attendance")
    object Profile : Screen("profile")
    object MyNavigation :Screen("main")
    object CheckInScreen :Screen("home/checkIn")
    object CheckOutScreen : Screen("home/checkOut")
    object ProfileUpdate : Screen("profile/updateProfile")
}