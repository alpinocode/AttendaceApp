package com.example.attendaceapp.ui.screen.updateProfile

import androidx.lifecycle.ViewModel
import com.example.attendaceapp.data.Repository

class UpdateProfileViewModel(private val repository: Repository) : ViewModel() {
    val messageSuccess = repository.messageSuccess
    val messageError = repository.messageError
    fun updateProfile(email: String,fullname: String, photoUrl:String,role:String, bio:String) = repository.updateProfile(email, fullname, photoUrl, role, bio)
}