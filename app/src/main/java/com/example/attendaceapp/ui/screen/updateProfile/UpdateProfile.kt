package com.example.attendaceapp.ui.screen.updateProfile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.attendaceapp.BuildConfig
import com.example.attendaceapp.R
import com.example.attendaceapp.data.Repository
import com.example.attendaceapp.ui.component.DialogComponent
import com.example.attendaceapp.ui.component.DialogComponentErrorPriority
import com.example.attendaceapp.ui.component.createImageFile
import com.example.attendaceapp.ui.screen.checkIn.initCloudinary
import com.example.attendaceapp.ui.viewModelFactory.ViewModelFactory
import com.example.attendaceapp.utils.UploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Objects

@Composable
fun UpdateProfile(
    navController: NavController,
    viewModel: UpdateProfileViewModel = viewModel(
        factory = ViewModelFactory(Repository())
    )
) {
    val username = Firebase.auth.currentUser

    val context = LocalContext.current
    var email by remember { mutableStateOf(username?.email.toString()) }
    var fullname by remember { mutableStateOf(username?.displayName.toString()) }
    var bio by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    val messageSuccess = viewModel.messageSuccess.observeAsState()
    val messageError = viewModel.messageError.observeAsState()

    val imageUri = remember { mutableStateOf(value = Uri.EMPTY) }
    initCloudinary(context)
    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
    ) {
        EditProfileTopBar()

        Spacer(modifier = Modifier.padding(top = 16.dp))
        EditPhotoProfileComponent(imageUri)

        EditProfileEdtComponent(
            email= email,
            fullName = fullname,
            bio = bio,
            role = role,
            onEmailChange = {email = it},
            onRoleChange = {role = it},
            onFullName = {fullname = it},
            onBioChange = {bio = it}
        )
        Button(
            onClick = {
                UploadImage(imageUri.value,
                    onSuccess = { url ->
                        viewModel.updateProfile(email, fullname, url, role, bio)
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
                textJudul = username?.displayName.toString(),
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

@Composable
fun EditProfileTopBar(
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "Back",

            )
        }

        Text(
            text = "Edit Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun EditPhotoProfileComponent(
    imageUri: MutableState<Uri>
) {
    val context = LocalContext.current
    val usernameData = Firebase.auth.currentUser


    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) {
        imageUri.value = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if(it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    Log.d("TAG", "Cek Url Gambar ${imageUri}")

    Column(
        modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box() {

            if (imageUri.value.path?.isNotEmpty() == true) {
                Image(
                    painter = rememberImagePainter(imageUri.value),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp).size(120.dp,120.dp).clip(CircleShape),
                )
            } else if(usernameData?.photoUrl != null) {
                Image(
                    painter = rememberImagePainter( usernameData.photoUrl.toString()),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp).clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.profileuser),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }
            Card(
                shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2D6AE0)
                ),
                onClick = {
                    val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if(permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(uri)
                    }else{
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(48.dp)
                    .offset(x = (-8).dp, y = (-8).dp)
                    .padding(start = 12.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_photo_camera_white_24),
                        contentDescription = "Camera Icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }


        }

        Text(
            "Change Photo",
            color = Color(0xFF2D6AE0),
            modifier = Modifier.padding(top = 12.dp)
        )

    }
}


@Composable
fun EditProfileEdtComponent(
    fullName:String,
    onFullName: (String) -> Unit,
    email:String,
    onEmailChange: (String) -> Unit,
    role:String,
    onRoleChange:(String) -> Unit,
    bio:String,
    onBioChange:(String) -> Unit
) {
    Column {
        Text(
            text = "Full Name",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = onFullName,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            label = { Text("Your Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 8.dp)
        )

        Text(
            text = "Email",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            label = { Text("JohnDoe@gmail.com") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 8.dp)
        )
        Text(
            text = "Role",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = role,
            onValueChange = onRoleChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            label = { Text("Sofware Enginer") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 8.dp)
        )
        Text(
            text = "Bio",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = bio,
            onValueChange = onBioChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            label = { Text("Tell us about your self") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 8.dp)
        )
    }
}