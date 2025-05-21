package com.example.attendaceapp.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.attendaceapp.R
import com.example.attendaceapp.data.Repository
import com.example.attendaceapp.model.Screen
import com.example.attendaceapp.ui.component.ProfileComponent
import com.example.attendaceapp.ui.component.TopBar
import com.example.attendaceapp.ui.component.cardComponentProfile
import com.example.attendaceapp.ui.viewModelFactory.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Repository())
    )
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
    ){
        ProfileComponent(navController)
        cardComponentProfile(
            text = "Change Password",
            icon = R.drawable.baseline_key_24,
            Color(0xFF000000),
            onClick = {

            }
        )
        cardComponentProfile(
            text = "Notification",
            icon = R.drawable.baseline_notifications_24,
            color = Color(0xFF000000),
            onClick = {}
        )
        cardComponentProfile(
            text = "Logout",
            icon = R.drawable.baseline_logout_24,
            color = Color(0xFFFF0000),
            onClick = {
                viewModel.signOut(context = context)
                navController.clearBackStack(true)
            }
        )
    }
}

