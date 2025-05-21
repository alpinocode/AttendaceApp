package com.example.attendaceapp.ui.component

import android.graphics.Paint.Align
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.attendaceapp.R
import com.example.attendaceapp.model.Screen
import com.example.attendaceapp.ui.theme.AttendaceAppTheme
import com.google.firebase.auth.FirebaseAuth
import okhttp3.internal.format
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeComponent(
    navController: NavController,
    role: String,
    name:String,
    image:String
) {
    val date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID")))
    val clockInImage = R.drawable.clock_in
    val clockOutImage = R.drawable.clock_out

    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
    ) {
        topBar(
            username = name,
            onClick = {},
            modifier = Modifier.padding(bottom = 16.dp),
            role = role,
            image = image
        )

        todaysJobCard(
            date,
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            cardButtonAttendance(
                image = clockInImage,
                text = "Clock In",
                color = Color(0xFF2D6AE0),
                onCardBtn = {navController.navigate(route = Screen.CheckInScreen.route)},
                textColor = Color.White
            )

            Spacer(modifier = Modifier.padding(start = 46.dp))
            cardButtonAttendance(
                image = clockOutImage,
                text = "Clock Out",
                color =  Color(0xFFE4E6EC),
                onCardBtn = {navController.navigate(route = Screen.CheckOutScreen.route)},
                textColor = Color.Black
            )
        }

        Text(
            text = "This Month",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            cardMonthAttendance(
                image = R.drawable.baseline_done_24,
                attendanceDeskripsion = "Present"
            )
            Spacer(modifier = Modifier.padding(start = 46.dp))
            cardMonthAttendance(
                image = R.drawable.baseline_close_24,
                attendanceDeskripsion = "Absent"
            )
        }

        Text(
            text = "Recent Activity",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}


@Composable
fun topBar(
    username:String,
    role:String,
    onClick:() -> Unit,
    image: String,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = colorResource(R.color.white))
            .padding(start = 16.dp, end = 24.dp, top = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val imageModifier = Modifier
                .size(70.dp)
                .clip(CircleShape)

            if (image != null) {
                Image(
                    painter = rememberImagePainter(image),
                    contentDescription = null,
                    modifier = imageModifier
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.profileuser),
                    contentDescription = null,
                    modifier = imageModifier
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 24.dp)
            ) {
                Text(
                    text = username,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                role.let {
                    Text(text = it)
                }
            }
        }

        IconButton(
            onClick = onClick,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null
            )
        }
    }

}


@Composable
fun cardButtonAttendance(
    image:Int,
    text:String,
    onCardBtn: () -> Unit,
    color: Color,
    textColor: Color
) {
    Card(
        onClick = onCardBtn,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.size(width = 170.dp, height = 140.dp),

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier.padding(top = 24.dp).size(42.dp)
            )
            Text(
                text,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

    }
}

@Composable
fun todaysJobCard(
    dateTodays:String,
    clockOutDate:String? = null,
    status:String? = null
) {
    Card(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "Today's Status",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = dateTodays,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
                Card(
                    modifier = Modifier
                        .height(28.dp),
                    shape = RoundedCornerShape(50),
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
                        modifier = Modifier.padding(horizontal = 12.dp)
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
                            fontSize = 16.sp
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Column {
                    Text(
                        "Clock In",
                    )
                    Text(
                        text = "09.00",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(24.dp))
                Column {
                    Text(
                        "Clock Out",
                    )
                    Text(
                        text = clockOutDate ?: "---",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(24.dp))
                Column {
                    Text(
                        "Working Hours",
                    )
                    Text(
                        text = "09.00",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

        }
    }

}

@Composable
fun cardMonthAttendance(
    image:Int,
    totalAttendance:String? = null,
    attendanceDeskripsion:String
) {
    Card(
        modifier = Modifier
            .padding(top = 12.dp)
            .size(width = 170.dp, height = 100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 24.dp, top = 28.dp)
        ) {
            Card(
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp, bottomStart = 25.dp, bottomEnd = 25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if(attendanceDeskripsion == "Present") colorResource(R.color.hijau_muda1) else colorResource(R.color.red_young)
                ),
                modifier = Modifier.size(width = 40.dp, height = 50.dp).align(Alignment.CenterVertically),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ){
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.padding(start = 14.dp))

            Column {
                Text(
                    text = totalAttendance ?: "0",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = attendanceDeskripsion,
                )
            }
        }

    }
}

