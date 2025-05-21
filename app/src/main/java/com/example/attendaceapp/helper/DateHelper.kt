package com.example.attendaceapp.helper

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateFormateHours():String {
    var dateHours  = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val updateHours = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm", Locale("in", "ID")))

            dateHours.value = updateHours
        }
    }

    return dateHours.value
}