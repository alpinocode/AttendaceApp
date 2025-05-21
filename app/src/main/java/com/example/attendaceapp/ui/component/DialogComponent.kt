package com.example.attendaceapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.attendaceapp.R

@Composable
fun DialogComponent(
    onDismiss:()->Unit,
    image:Int,
    text:String,
    textJudul:String
) {
    Dialog(onDismissRequest = {onDismiss()}, properties = DialogProperties(
        dismissOnBackPress = false, dismissOnClickOutside = false
    )){
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().padding(8.dp),
        ) {
            Column(
                Modifier.fillMaxWidth().background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.Red.copy(alpha = 0.8F)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth
                    )
                }
                Text(
                    text = "Great Job, ${textJudul} \uD83D\uDC4D",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp), fontSize = 20.sp
                )

                Text(
                    text = text,
                    modifier = Modifier.padding(8.dp)
                )

                Row(Modifier.padding(top = 10.dp)) {

                    Button(
                        onClick = { onDismiss()},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2D6AE0)
                        ),
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Exit")
                    }
                }
            }
        }
    }
}

@Composable
fun DialogComponentErrorPriority(
    onDismiss:()->Unit,
    image:Int,
    text:String,
    showDialogErrorPriority:Boolean
) {
    if(showDialogErrorPriority) {
        Dialog(onDismissRequest = {onDismiss()}, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )){
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().padding(8.dp),
            ) {
                Column(
                    Modifier.fillMaxWidth().background(Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(Color.Red.copy(alpha = 0.8F)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painter = painterResource(image),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                    Text(
                        text = "Error Message !",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally), fontSize = 20.sp
                    )

                    Text(
                        text = text,
                        modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally)
                    )

                    Row(Modifier.padding(top = 10.dp)) {

                        Button(
                            onClick = { onDismiss()},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2D6AE0)
                            ),
                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1F)
                        ) {
                            Text(text = "Exit")
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogErrorComponent(onDismiss: () -> Unit, messageError: String, showBottomSheet:Boolean) {
    if(showBottomSheet) {
        ModalBottomSheet(onDismissRequest = {onDismiss()}) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ){
                Image(
                    painter = painterResource(R.drawable.warning_red),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = "Error Message!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = messageError
                )
            }

        }
    }

}

@Preview
@Composable
fun DialogErrorPreview() {
    DialogErrorComponent(
        onDismiss = {},
        messageError = "Errornya gk jelas",
        showBottomSheet = true
    )
}
@Preview
@Composable
fun DialogComponentPreview() {
    DialogComponent(
        image = R.drawable.attendancedone,
        text = "\uD83C\uDF89 Yeay! Kamu berhasil check-in. Terima kasih sudah hadir hari ini",
        textJudul = "Alfino Hasan",
        onDismiss = {}
    )
}