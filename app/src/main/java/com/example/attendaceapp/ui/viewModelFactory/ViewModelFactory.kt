package com.example.attendaceapp.ui.viewModelFactory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.attendaceapp.data.Repository
import com.example.attendaceapp.ui.screen.attendance.AttendanceViewModel
import com.example.attendaceapp.ui.screen.checkIn.CheckInViewModel
import com.example.attendaceapp.ui.screen.checkOut.CheckOutViewModel
import com.example.attendaceapp.ui.screen.home.HomeViewModel
import com.example.attendaceapp.ui.screen.login.LoginViewModel
import com.example.attendaceapp.ui.screen.profile.ProfileViewModel
import com.example.attendaceapp.ui.screen.register.RegisterViewModel
import com.example.attendaceapp.ui.screen.updateProfile.UpdateProfileViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
          return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CheckInViewModel::class.java)) {
            return CheckInViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(CheckOutViewModel::class.java)) {
            return CheckOutViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(AttendanceViewModel::class.java)) {
            return AttendanceViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(UpdateProfileViewModel::class.java)) {
            return UpdateProfileViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw Exception("Model Clas is Asiggnable : ${modelClass.name}")
    }
}