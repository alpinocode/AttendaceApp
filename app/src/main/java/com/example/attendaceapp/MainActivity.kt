package com.example.attendaceapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.attendaceapp.model.Screen
import com.example.attendaceapp.ui.navigation.MyNavigation
import com.example.attendaceapp.ui.screen.checkIn.CheckInScreen
import com.example.attendaceapp.ui.screen.checkOut.CheckOutScreen
import com.example.attendaceapp.ui.screen.register.RegisterScreen
import com.example.attendaceapp.ui.screen.login.LoginScreen
import com.example.attendaceapp.ui.theme.AttendaceAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var userAuth = FirebaseAuth.getInstance().currentUser
            AttendaceAppTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = if(userAuth != null)Screen.MyNavigation.route else "login"
                ) {
                    composable(route = Screen.Login.route) {
                        LoginScreen(navController)
                    }
                    composable(route = Screen.Register.route) {
                        RegisterScreen(navController = navController)
                    }

                    composable(route = Screen.MyNavigation.route) {
                        MyNavigation()
                    }

                }
            }
        }
    }
}
