package com.example.attendaceapp.ui.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.attendaceapp.R
import com.example.attendaceapp.data.Repository
import com.example.attendaceapp.ui.component.HomeComponent
import com.example.attendaceapp.ui.viewModelFactory.ViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Repository())
    )
) {
    val dataUser = viewModel.dataUser.observeAsState()
    val isLoadingState by viewModel.indicatorStateLoading.observeAsState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Konten utama tetap tampil
        HomeComponent(
            navController = navController,
            role = dataUser.value?.role.orEmpty(),
            image = dataUser.value?.image.orEmpty(),
            name = dataUser.value?.name.orEmpty()
        )

        // Overlay loading di tengah layar
        if (isLoadingState == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)), // opsional: overlay gelap
                contentAlignment = Alignment.Center
            ) {
                indicatorLoading(isLoadingState!!)
            }
        }
    }



}

@Composable
fun indicatorLoading(isLoading:Boolean) {
    if(!isLoading) return
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = Color(0xFF006FE5),
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}