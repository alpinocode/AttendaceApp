package com.example.attendaceapp.ui.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.attendaceapp.R
import com.example.attendaceapp.model.Screen

@Composable
fun LoginComponent(
    navController: NavController,
    email:String,
    password:String,
    onEmailChange:(String)->Unit,
    onPasswordChange: (String)->Unit,
    onBtnLogin : () -> Unit,
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.attendance_app),
            contentDescription = "Attendance App",
            modifier = Modifier.size(width = 600.dp, height = 125.dp)
        )
        Text(
            text = "Welcome Back Attendance App",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Email",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            label = { Text("JohnDoe") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 16.dp)
        )

        Text(
            text = "Password",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )


        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility}
                ) {
                    Icon(
                        painter = painterResource(id = if (passwordVisibility) R.drawable.visible else R.drawable.hide),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 8.dp)
        )
        Button(
            onClick = onBtnLogin,
            shape = RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp,
                bottomStart = 8.dp,
                bottomEnd = 8.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2D6AE0)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
        Text(
            text = "Create New Account",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF2D6AE0),

            modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally).clickable {
                navController.navigate(Screen.Register.route)
            }
        )
    }
}

@Preview
@Composable
fun loginComponentPreview() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val navController  = rememberNavController()
    LoginComponent(
        navController,
        email = email,
        password = password,
        onEmailChange = { email = it},
        onPasswordChange = {password = it},
        onBtnLogin = {},
    )
}