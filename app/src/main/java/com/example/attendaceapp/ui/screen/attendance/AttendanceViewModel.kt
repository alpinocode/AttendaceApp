package com.example.attendaceapp.ui.screen.attendance

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.attendaceapp.data.Repository

@RequiresApi(Build.VERSION_CODES.O)
class AttendanceViewModel(private val repository: Repository) : ViewModel() {
    val dataAttendaceUser = repository.dataAttendanceUser
    init {
        getAttendance()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAttendance() = repository.getAttendance()
}