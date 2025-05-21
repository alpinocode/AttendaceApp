@file:Suppress("UNUSED_EXPRESSION")

package com.example.attendaceapp.ui.screen.checkIn

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cloudinary.android.MediaManager
import com.example.attendaceapp.R
import com.example.attendaceapp.data.Repository
import com.example.attendaceapp.ui.component.CardImage
import com.example.attendaceapp.ui.component.CardLocation
import com.example.attendaceapp.ui.component.DialogComponent
import com.example.attendaceapp.ui.component.DialogComponentErrorPriority
import com.example.attendaceapp.ui.component.TopBarAttendance
import com.example.attendaceapp.ui.component.getAddressFromLocation
import com.example.attendaceapp.ui.viewModelFactory.ViewModelFactory
import com.example.attendaceapp.utils.UploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CheckInScreen(
    navController: NavController,
    viewModel: CheckInViewModel = viewModel(
        factory = ViewModelFactory(Repository())
    )
) {
    val username = Firebase.auth.currentUser
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf(value = Uri.EMPTY) }
    val location = remember { mutableStateOf<Location?>(null) }
    val dateHours = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm", Locale("in", "ID")))
    val messageSuccess = viewModel.messageSuccess.observeAsState()
    val messageError = viewModel.messageError.observeAsState()
    initCloudinary(context)
    val locationNow = getAddressFromLocation(context, location = location)
    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
    ) {
        TopBarAttendance(
            username =  username?.email.toString(),
            navController,
            modifier = Modifier
        )
        CardLocation(
            location = location
        )
        CardImage(
            imageUri
        )
        Button(
            onClick = {
                UploadImage(imageUri.value,
                    onSuccess = { url ->
                        locationNow?.let {
                            viewModel.checkInCreate("Alfin Hasan",url,
                                it,dateHours )
                        }

                    })

            },
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D6AE0)),
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
        ) {
            Text(
                "Check In",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (messageSuccess.value != null) {
            DialogComponent(
                image = R.drawable.attendancedone,
                text = messageSuccess.value.toString(),
                textJudul = username?.email.toString(),
                onDismiss = {
                    navController.popBackStack()
                }
            )
        }
        if (messageError.value != null){
            DialogComponentErrorPriority(
                onDismiss = {navController.popBackStack()},
                text = messageError.value.toString(),
                image = R.drawable.checkin_ready,
                showDialogErrorPriority = true
            )
        }

    }



}




fun initCloudinary(context: Context) {
    try {
        MediaManager.get() // Cek apakah sudah pernah init
    } catch (e: IllegalStateException) {
        val config = hashMapOf("cloud_name" to "dooe650it")
        MediaManager.init(context.applicationContext, config)
        Log.d("Cloudinary", "MediaManager initialized")
    }
}

