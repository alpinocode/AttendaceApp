package com.example.attendaceapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendaceapp.R

@Composable
fun CardAttendanceUserComponent(
    dayText:String,
    checkInHours:String,
    checkOutHours:String,
    status:String,
) {

    Card(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
            .size(width = 170.dp, height = 120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column() {
                    Text(
                        dayText,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Thursday"
                    )
                }


                Card(
                    modifier = Modifier
                        .height(28.dp),
                    shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp, bottomStart = 6.dp, bottomEnd = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =  when{
                            status == "Present" -> {
                                colorResource(R.color.hijau_muda1)
                            }
                            status == "Absent" -> {
                                colorResource(R.color.red_young)
                            }
                            status == "Late" -> {
                                colorResource(R.color.light_yellow)
                            }
                            else -> colorResource(R.color.gray_unknown_bg)
                        }
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(horizontal = 12.dp).fillMaxHeight()
                    ) {
                        Text(
                            text = status ?: "Unknown",
                            color = when{
                                status == "Present" -> {
                                    colorResource(R.color.hijau_muda)
                                }
                                status == "Absent" -> {
                                    colorResource(R.color.red)
                                }
                                status == "Late" -> {
                                    colorResource(R.color.yellow)
                                }
                                else -> colorResource(R.color.gray_unknown)
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }


            Row(
                modifier = Modifier.padding(top = 24.dp)
            ) {
                HoursComponent(
                    checkInHours,
                    "In: "
                )
                Spacer(modifier = Modifier.padding(start = 12.dp))
                HoursComponent(
                    checkOutHours,
                    "Out :"
                )
            }
        }

    }
}


@Composable
fun HoursComponent(
    attendaceHours:String,
    textHoursStatus:String,
) {
    Row {
        Image(
            painter = painterResource(R.drawable.baseline_access_time_filled_24),
            contentDescription = null,
            modifier = Modifier
        )

        Text(
            textHoursStatus,
            modifier = Modifier.padding(start = 6.dp)
        )
        Text(
            attendaceHours,
            modifier = Modifier.padding(start = 4.dp)
        )

    }
}


@Preview(showBackground = true)
@Composable
fun  CardAttendanceUserComponentPreview() {
    CardAttendanceUserComponent(
        "May 8, 2025",
        "09:02",
        "17:30",
        "Present"
    )
}