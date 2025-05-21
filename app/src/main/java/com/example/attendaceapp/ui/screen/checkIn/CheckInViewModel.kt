package com.example.attendaceapp.ui.screen.checkIn

import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.attendaceapp.data.Repository

@RequiresApi(Build.VERSION_CODES.O)
class CheckInViewModel(private val repository: Repository) : ViewModel() {
    val messageSuccess = repository.messageSuccess
    val messageError = repository.messageError
    init {
        getCheckIn()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkInCreate(name:String,image:String, location: String, dateHours:String) = repository.createCheckIn(name,image, location,dateHours)
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCheckIn() = repository.getCheckIn()
}