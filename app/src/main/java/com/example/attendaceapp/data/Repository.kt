package com.example.attendaceapp.data

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.attendaceapp.model.AttendanceUser
import com.example.attendaceapp.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.EventListener
import java.util.Locale
import kotlin.random.Random


class Repository {
    private val _messageSuccess = MutableLiveData<String?>()
    val messageSuccess:LiveData<String?> = _messageSuccess

    private val _messageErrorPriority = MutableLiveData<String?>()
    val messageErrorPriority:LiveData<String?> = _messageErrorPriority
    private val _messageError = MutableLiveData<String?>()
    val messageError:LiveData<String?> = _messageError

    private val _dateCheckIn = MutableLiveData<String?>()
    val dateCheckIn:LiveData<String?> = _dateCheckIn

    private val _dataAttendanceUser = MutableLiveData<List<AttendanceUser>>()
    val dataAttendanceUser:LiveData<List<AttendanceUser>> = _dataAttendanceUser

    private val _dataUser = MutableLiveData<UserAuth>()
    val dataUser:LiveData<UserAuth> = _dataUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading
    private val _dataPresent = MutableLiveData<String?>()
    val dataPresent:LiveData<String?> = _dataPresent
    fun register(name: String, email: String, password: String, confirmPassword: String) {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        when {
            name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                _messageError.postValue("Semua field wajib diisi")
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches() -> {
                _messageError.postValue("Format email tidak valid")
                return
            }
            password != confirmPassword -> {
                _messageError.postValue("Password dan konfirmasi tidak sama")
                return
            }
            trimmedPassword.length < 6 -> {
                _messageError.postValue("Password minimal 6 karakter")
                return
            }
        }

        _messageError.value = null

        val updateProfile = userProfileChangeRequest {
            displayName = name.trim()
        }


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(trimmedEmail, trimmedPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Firebase.auth.currentUser?.updateProfile(updateProfile)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                _messageSuccess.postValue("Register berhasil")
                            } else {
                                _messageError.postValue("Gagal menyimpan profil pengguna")
                            }
                        }
                } else {
                    val errorMsg = task.exception?.localizedMessage ?: "Register gagal"
                    _messageError.postValue(errorMsg)
                }
            }
    }

    fun login(email: String, password: String) {
        _messageSuccess.postValue(null)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _messageError.postValue("Input Email Harus Berformat Email")
            return
        }
        if (email.isEmpty() || password.isEmpty()) {
            _messageError.postValue("Semua Field Not Empty")
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                _messageSuccess.postValue("Login Success")
            } else {
                _messageError.postValue("Login Gagal")
            }
        }
    }

    fun registerByFirebaseRealtime(id:String, name: String, email: String) {
        val user = UserAuth(name.trim(), email.trim())
        Firebase.database.getReference("Users")
            .child(id)
            .setValue(user)
    }

    fun signOut(context: Context) {
        _messageSuccess.value = null
        _messageError.value = null

        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        val crendetialManager = CredentialManager.create(context)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val clearRequest = ClearCredentialStateRequest()
                crendetialManager.clearCredentialState(clearRequest)
            } catch (e:Exception) {
                Log.e("TAG", "Couldn't clear user credentials: ${e.localizedMessage}")
            }
        }
    }

    fun updateProfile(email: String,fullname: String, photoUrl:String,role:String, bio:String) {
        val user = Firebase.auth.currentUser
        val userDb = Firebase.database.getReference("Users").child(user?.uid.toString())
        val profileUpdates = userProfileChangeRequest {
            displayName = fullname
            photoUri = Uri.parse(photoUrl)

        }
        val dataUserUpdate = UserAuth(name = fullname, role =role, image = profileUpdates.photoUri.toString(), email = email, bio = bio)
        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.updateEmail(email)
                    userDb.setValue(dataUserUpdate)
                    _messageSuccess.value = "Update Profile Success"
                } else {
                    _messageError.value = "Update Profile Gagal"
                }
            }
    }

    fun getUser() {
        _isLoading.value = true
        val userAuth = FirebaseAuth.getInstance().currentUser
        val userRefenrence = Firebase.database.getReference("Users").child(userAuth?.uid.toString())

        userRefenrence.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataUserAuth = snapshot.getValue(UserAuth::class.java)

                dataUserAuth.let {
                    _dataUser.value = it
                }
                _isLoading.value = false
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createCheckIn(
        name:String,
        image:String,
        location:String,
        dateHours:String
    ) {
        _messageError.value = null
        _messageSuccess.value = null
        if(image.isEmpty() || location.isEmpty()) {
            _messageError.postValue("Data Image Atau Location Tidak Boleh Kosong")
            return
        }

        val databaseRefenrence = Firebase.database.getReference(name)

        val id = Random.nextInt(2000)
        val dateDayNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("in", "ID")))
        val dateMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale("in", "ID")))
        val dataRequest = AttendanceUser(id= id, date = dateDayNow, imageCheckIn = image, locationCheckIn = location, dateHoursCheckIn = dateHours, dateMonth = dateMonth)
        databaseRefenrence.child("AttendanceUser").child(dateMonth).child(dateDayNow).setValue(dataRequest)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _messageSuccess.postValue("\uD83C\uDF89 Yeay! Kamu berhasil check-in. Terima kasih")
                } else {
                    _messageError.postValue("Check In Gagal")
                }
            }
            .addOnFailureListener{
                Log.d("TAG", "CEk Errornya ${it.message}")
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createCheckOut(name: String, date:String, image: String, location: String, status:String) {
        _messageError.value = null
        _messageSuccess.value = null
        if(image.isEmpty() || location.isEmpty()) {
            _messageError.value = " Image Atau Location Tidak Boleh Kosong"
            return
        }


        val databaseReference = Firebase.database.getReference(name)
        val dateDayNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("in", "ID")))
        val dateMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale("in", "ID")))
        val dataCheckOutMap = mutableMapOf<String, Any>(
            "imageCheckOut" to image,
            "locationCheckOut" to location,
            "dateHoursCheckOut" to date,
            "status" to status
        )
        databaseReference.child("AttendanceUser").child(dateMonth).child(dateDayNow).updateChildren(dataCheckOutMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _messageSuccess.value = "\uD83C\uDF89 Yeay! Kamu berhasil check-out. Terima kasih sudah hadir hari ini. Hati-hati di jalan!"
            } else {
                _messageErrorPriority.value = "Check Out Gagal"
            }

        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun createAbsent() {
        val name = Firebase.auth.currentUser?.displayName.toString()
        _messageError.value = null
        _messageSuccess.value = null

        val databaseRefenrence = Firebase.database.getReference(name)

        val id = Random.nextInt(2000)
        val dateDayNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("in", "ID")))
        val dateMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale("in", "ID")))

        val attendancePath = databaseRefenrence.child("AttendanceUser").child(dateMonth).child(dateDayNow)

        attendancePath.get().addOnSuccessListener { snapshot ->
            if (!snapshot.exists()) {
                val dataAbsent = AttendanceUser(
                    id = id,
                    date = dateDayNow,
                    imageCheckIn = "",
                    locationCheckIn = "",
                    dateHoursCheckIn = "",
                    dateMonth = dateMonth,
                    status = "Absent"
                )

                attendancePath.setValue(dataAbsent)
                    .addOnSuccessListener {
                        _messageSuccess.postValue("User tidak melakukan check-in hari ini, status diset Absent")
                    }
                    .addOnFailureListener {
                        _messageError.postValue("Gagal menyimpan data Absent: ${it.message}")
                    }
            } else {
                _messageSuccess.postValue("User sudah check-in hari ini, tidak perlu tandai Absent")
            }
        }.addOnFailureListener {
            _messageError.postValue("Gagal membaca data: ${it.message}")
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun getCheckIn() {
        val name = Firebase.auth.currentUser?.displayName.toString()
        val databaseReference = Firebase.database.getReference(name)
        val dateDayNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("in", "ID")))
        val dateMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale("in", "ID")))
        databaseReference.child("AttendanceUser").child(dateMonth).child(dateDayNow).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataAttendance = snapshot.getValue(AttendanceUser::class.java)

                if(dataAttendance?.dateHoursCheckIn == null) {
                    _messageErrorPriority.value = "Kamu Belum Melakukan CheckIn"
                } else if (dataAttendance.dateHoursCheckOut != null){
                    _messageError.value = "Kamu sudah Absent Hari Ini, Terimakasih atas kehadiran anda untuk Hari ini"
                } else {
                    _dateCheckIn.value = dataAttendance.dateHoursCheckIn
                }
            }

            override fun onCancelled(snapshot: DatabaseError) {

            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAttendance() {
        val name = "Alfin Hasan"
        val databaseReference = Firebase.database.getReference(name)
        val dateMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale("in", "ID")))
        databaseReference.child("AttendanceUser").child(dateMonth).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val dataAttendaceUser = mutableListOf<AttendanceUser>()
                for(itemSnapshot in snapshot.children) {
                    val dataAttendance = itemSnapshot.getValue(AttendanceUser::class.java)

                    if (dataAttendance != null) {
                        dataAttendaceUser.add(dataAttendance)
                    }
                    if (dataAttendance?.status == "Present"){

                    }
                }
                val presentCount = dataAttendaceUser.count { it.status == "Present" }
                _dataAttendanceUser.value = dataAttendaceUser
                _dataPresent.value = presentCount.toString()
            }

            override fun onCancelled(snapshot: DatabaseError) {

            }
        })
    }
    companion object {
        private val TAG = "Repository"
    }
}
