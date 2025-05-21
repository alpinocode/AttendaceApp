package com.example.attendaceapp.ui.screen.register

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.attendaceapp.data.Repository
import com.example.attendaceapp.model.Screen
import com.example.attendaceapp.ui.viewModelFactory.ViewModelFactory
import com.example.laundry_app.ui.component.RegisterComponent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(
        factory = ViewModelFactory(repository = Repository())
    ),
    navController: NavController
) {
    val userAuth= FirebaseAuth.getInstance().currentUser
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirPassword by rememberSaveable { mutableStateOf("") }
    RegisterComponent (
        name = name,
        email = email,
        password = password,
        confirmPassword = confirPassword,
        onNameChange =  { name = it },
        onEmailChange = { email = it },
        onPasswordChange = {password = it },
        onConfirmPasswordChange = { confirPassword = it},
        btnRegister = {
                viewModel.register(name.trim(), email.trim(), password.trim(), confirPassword.trim())
        }
    )

    val context = LocalContext.current
    val successMessage by viewModel.messageSuccess.observeAsState()
    val errorMessage by viewModel.messageError.observeAsState()

    LaunchedEffect(successMessage)  {
        if(successMessage != null) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            viewModel.registerByFirebaseRl(userAuth?.uid.toString(), userAuth?.displayName.toString(), userAuth?.email.toString())
            navController.navigate(Screen.MyNavigation.route){
                popUpTo(0)
            }
        }
    }
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}