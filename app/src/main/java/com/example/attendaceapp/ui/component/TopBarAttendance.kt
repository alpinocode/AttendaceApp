package com.example.attendaceapp.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.attendaceapp.R
import com.example.attendaceapp.model.Screen
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopBarAttendance(
    username:String,
    navController: NavController,
    modifier: Modifier
) {
    val dateMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("in", "ID")))
    var dataDateHours = remember { mutableStateOf("") }
    val dateHours = LocalDateTime.now().format(
        DateTimeFormatter.ofPattern("HH:mm", Locale("in", "ID"))
    )
    dataDateHours.value = dateHours


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.border(width = 1.dp, color = colorResource(R.color.white)).fillMaxWidth().padding(start = 16.dp, end = 8.dp, top = 8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
            contentDescription = null,
            modifier = Modifier.clickable {
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Home.route) {
                        inclusive = true
                    }
                }
            }.size(32.dp)
        )
        Image(
            painter = painterResource(R.drawable.profileuser),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Column (
            modifier = Modifier.padding(start = 8.dp, end = 20.dp, bottom = 8.dp)
        ) {
            Text(
                text = username,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_calendar_today_24),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = dateMonth
                )
            }

        }

        Text(
            dataDateHours.value.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 24.dp, end = 8.dp)
        )
    }
}
