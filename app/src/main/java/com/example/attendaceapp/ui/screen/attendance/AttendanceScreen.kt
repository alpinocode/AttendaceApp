package com.example.attendaceapp.ui.screen.attendance

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.attendaceapp.data.Repository
import com.example.attendaceapp.ui.component.CardAttendanceUserComponent
import com.example.attendaceapp.ui.viewModelFactory.ViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttendanceScreen(
    viewModel: AttendanceViewModel = viewModel(
        factory = ViewModelFactory(Repository())
    )
) {

    val context = LocalContext.current

    val attendanceList = viewModel.dataAttendaceUser.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        if (attendanceList != null) {
            attendanceList.value.forEach{ data ->
                CardAttendanceUserComponent(
                    dayText = data.date.toString(),
                    checkOutHours = data.dateHoursCheckOut.toString(),
                    checkInHours = data.dateHoursCheckIn.toString(),
                    status = data.status.toString()
                )
            }
        }
    }

}