package com.example.attendaceapp.ui.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.attendaceapp.data.Repository

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(private val repository: Repository) : ViewModel() {
    val dataUser = repository.dataUser
    val indicatorStateLoading = repository.isLoading
    init {
        getUser()

    }
    fun getUser() = repository.getUser()
}