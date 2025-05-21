package com.example.attendaceapp.ui.screen.checkOut

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.attendaceapp.data.Repository

@RequiresApi(Build.VERSION_CODES.O)
class CheckOutViewModel(private val repository: Repository) :ViewModel(){
    val messageSuccess = repository.messageSuccess
    val messageErrorPriority = repository.messageErrorPriority
    val dateHoursCheckIn = repository.dateCheckIn
    val messageError = repository.messageError

    init {
        getCheckIn()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkOut(name:String, date:String, image:String, location:String,status:String)= repository.createCheckOut(name,date, image, location, status)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCheckIn() = repository.getCheckIn()
}