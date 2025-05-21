package com.example.attendaceapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.attendaceapp.model.Screen
import com.example.attendaceapp.ui.screen.attendance.AttendanceScreen
import com.example.attendaceapp.ui.screen.checkIn.CheckInScreen
import com.example.attendaceapp.ui.screen.checkOut.CheckOutScreen
import com.example.attendaceapp.ui.screen.home.HomeScreen
import com.example.attendaceapp.ui.screen.profile.ProfileScreen
import com.example.attendaceapp.ui.screen.updateProfile.UpdateProfile

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavigation() {
    val navController= rememberNavController()


    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    val showButtomNavigation = when(currentRoute) {
            Screen.Home.route,
            Screen.Attendance.route,
            Screen.Profile.route -> true
            else -> false
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { if (showButtomNavigation)  BottomNavigation(navController) }
    ) { innerPadding ->

        val graph = navController.createGraph(
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Attendance.route) {
                AttendanceScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController
                )
            }
            composable(Screen.CheckInScreen.route) {
                CheckInScreen(navController = navController)
            }
            composable(Screen.CheckOutScreen.route) {
                CheckOutScreen(navController = navController)
            }
            composable(Screen.ProfileUpdate.route) {
                UpdateProfile(navController)
            }

        }
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )

    }
}