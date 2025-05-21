package com.example.attendaceapp.ui.screen.login

import androidx.lifecycle.ViewModel
import com.example.attendaceapp.data.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
    val messageSuccess = repository.messageSuccess
    val messageError = repository.messageError

    fun login(email:String, password:String) = repository.login(email, password)
}