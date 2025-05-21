package com.example.attendaceapp.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.attendaceapp.data.Repository
import com.example.attendaceapp.model.Screen
import com.example.attendaceapp.ui.component.LoginComponent
import com.example.attendaceapp.ui.viewModelFactory.ViewModelFactory

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(Repository())
    )
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    LoginComponent(
        navController,
        email = email,
        password = password,
        onEmailChange = {email = it},
        onPasswordChange = {password = it},
        onBtnLogin = {
            viewModel.login(email, password)
        },
    )

    val context = LocalContext.current
    val successMessage by viewModel.messageSuccess.observeAsState()
    val errorMessage by viewModel.messageError.observeAsState()

    LaunchedEffect(successMessage) {
        if(successMessage != null) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.MyNavigation.route) {
                popUpTo(0)
            }
        }
    }

    LaunchedEffect(errorMessage) {
        if(errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

}