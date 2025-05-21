package com.example.attendaceapp.utils

import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback



fun UploadImage(
    uri: Uri,
    onSuccess: (String) -> Unit
) {
    val uploadPreset = "attendance"
    MediaManager.get().upload(uri).unsigned(uploadPreset).callback(object : UploadCallback {
        override fun onStart(requestId: String) {
            Log.d("Cloudinary Quickstart", "Upload start")
        }

        override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
            Log.d("Cloudinary Quickstart", "Upload progress")
        }

        override fun onSuccess(requestId: String, resultData: Map<*, *>) {
            Log.d("Cloudinary Quickstart", "Upload success")
            val secureUrl = resultData.get("secure_url")
            secureUrl.let {
                onSuccess(it.toString())
            }
        }

        override fun onError(requestId: String, error: ErrorInfo) {
            Log.d("Cloudinary Quickstart", "Upload failed ${error.description}")
        }

        override fun onReschedule(requestId: String, error: ErrorInfo) {
        }
    }).dispatch()

}
