package com.example.attendaceapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.attendaceapp.R
import com.example.attendaceapp.model.Screen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileComponent(
    navController: NavController
) {
    val imageProfile = R.drawable.baseline_edit_square_24
    Column(
    ) {
        TopBar(
            "Profile",
            image = imageProfile,
            onClick = {navController.navigate(route = Screen.ProfileUpdate.route)}
        )

        profileDetail()

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            attendaceReport(
                totalAttendance = "98%",
                attendanceDeskripsion = "Attendance",
                Color(0xFF2962FF)
            )
            attendaceReport(
                totalAttendance = "45 H",
                attendanceDeskripsion = "This Week",
                Color(0xFF2E7D32)
            )
            attendaceReport(
                totalAttendance = "1",
                attendanceDeskripsion = "Late Days",
                color = Color(0xFF8E24AA)
            )
        }

        Spacer(modifier = Modifier.padding(top = 32.dp))

    }
}

@Composable
fun profileDetail() {
    val usernameData = Firebase.auth.currentUser
    Column(
        modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if(usernameData?.photoUrl == null) {
            Image(
                painter = painterResource(R.drawable.profileuser),
                contentDescription = null,
                modifier = Modifier.size(120.dp).clip(CircleShape)
            )
        } else {
            Image(
                painter = rememberImagePainter( usernameData.photoUrl.toString()),
                contentDescription = null,
                modifier = Modifier.size(120.dp).clip(CircleShape)
            )
        }
        Text(
            text = usernameData?.displayName.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun attendaceReport(
    totalAttendance: String,
    attendanceDeskripsion:String,
    color: Color
) {

    Card(
        modifier = Modifier
            .padding(top = 12.dp)
            .size(width = 120.dp, height = 90.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(start = 8.dp, top = 12.dp, end = 8.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = totalAttendance ?: "0",
                color = color,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = attendanceDeskripsion,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun cardComponentProfile(
    text:String,
    icon:Int,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 12.dp).fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(

            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(start = 12.dp))
                Text(
                    text,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }

            Image(
                painter = painterResource(R.drawable.baseline_navigate_next_24),
                contentDescription = null,
            )
        }
    }

}