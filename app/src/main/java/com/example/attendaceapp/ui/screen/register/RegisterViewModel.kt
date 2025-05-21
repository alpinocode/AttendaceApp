package com.example.attendaceapp.ui.screen.register

import androidx.lifecycle.ViewModel
import com.example.attendaceapp.data.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    val messageSuccess = repository.messageSuccess
    val messageError = repository.messageError
    fun register(name:String, email:String, password:String, confirmPassword:String) = repository.register(name, email, password, confirmPassword)
    fun registerByFirebaseRl(id:String, name: String, email: String) = repository.registerByFirebaseRealtime(id, name, email)
}
