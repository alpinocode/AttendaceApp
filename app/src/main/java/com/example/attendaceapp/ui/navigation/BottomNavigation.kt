package com.example.attendaceapp.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.attendaceapp.R
import com.example.attendaceapp.model.NavigationItem
import com.example.attendaceapp.model.Screen

@Composable
fun BottomNavigation(
    navController: NavController,
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    val navigationItem = listOf(
        NavigationItem(
            title = "Home",
            icon = R.drawable.home,
            route = Screen.Home.route
        ),
        NavigationItem(
            title = "Attendance",
            icon = R.drawable.attendance,
            route = Screen.Attendance.route
        ),
        NavigationItem(
            title = "Profile",
            icon = R.drawable.people,
            route = Screen.Profile.route,
        )
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        navigationItem.forEachIndexed { index, item ->
            NavigationBarItem(
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                    selectedNavigationIndex.intValue = index

                },
                selected = selectedNavigationIndex.intValue == index,
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (index == selectedNavigationIndex.intValue) Color.Blue else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if(index == selectedNavigationIndex.intValue) Color.Blue else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}