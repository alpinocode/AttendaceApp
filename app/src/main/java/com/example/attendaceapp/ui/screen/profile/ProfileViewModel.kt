package com.example.attendaceapp.ui.screen.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.attendaceapp.data.Repository

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    fun signOut(context: Context) = repository.signOut(context)
}