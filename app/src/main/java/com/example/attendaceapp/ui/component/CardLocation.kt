@file:Suppress("UNUSED_EXPRESSION")

package com.example.attendaceapp.ui.component

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.attendaceapp.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CardLocation(
    location:MutableState<Location?>
) {

    val context = LocalContext.current

    val locationManager = remember {
        LocationManager(
            context = context,
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        )
    }

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )



    val coroutineScope  =  rememberCoroutineScope()
    Card(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .size(width = 170.dp, height = 100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                "Current Location",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.border(width = 1.dp, color = colorResource(R.color.gray_unknown_bg)))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(top = 16.dp).clickable {
                    if (!locationPermissions.allPermissionsGranted || locationPermissions.shouldShowRationale) {
                        locationPermissions.launchMultiplePermissionRequest()
                    } else {
                        coroutineScope.launch {
                            location.value = locationManager.getLocation()
                        }
                    }
                }.fillMaxSize()

            ) {
                if(location != null) {
                    Text(
                        "Location Ready",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Image(
                        painter = painterResource(R.drawable.baseline_location_on_24),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        "Location not fetched",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Image(
                        painter = painterResource(R.drawable.baseline_location_on_24),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }

            }
        }

    }
}

class LocationManager(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
){
    suspend fun getLocation(): Location? {
        val hasGrantedFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasGrantedCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE
        ) as android.location.LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)

        if(!isGpsEnabled && !(hasGrantedCoarseLocationPermission || hasGrantedFineLocationPermission)) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if(isComplete) {
                    if(isSuccessful) {
                        cont.resume(result)
                    } else{
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }

                addOnCompleteListener {
                    cont.resume(result)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }

}

fun getAddressFromLocation(context: Context, location: MutableState<Location?>):String? {
    return try {
        val geocoder = Geocoder(context, Locale("in", "ID"))
        val address = location.value.let {
            geocoder.getFromLocation(it!!.latitude , it.longitude, 1)
        }
        if(!address.isNullOrEmpty()) {
            address[0].getAddressLine(0) ?: "Unknown Address"
        } else {
            "Adress Not Found"
        }
    } catch (e:Exception) {
        e.printStackTrace()
        Log.d("Location", "Cek Errornya ${e.message}")
        null

    }
}

