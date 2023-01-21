package com.example.hotelzo

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class Email {
    companion object{
        private const val apiKey = "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"

        fun sendEmail(message: String, subject:String, email: String, name: String){
            val json = """
            {
                "sender": {
                    "name": "Hotelzo",
                    "email": "mkajic20@student.foi.hr"
                },
                "to": [
                    {
                        "email": "$email",
                        "name": "$name"
                    }
                ],
                "subject": "$subject",
                "htmlContent": "$message"
            }
            """

            val client = OkHttpClient.Builder().build()
            val mediaType = "application/json".toMediaTypeOrNull()
            val body = json.toRequestBody(mediaType)
            val request = Request.Builder()
                .url("https://api.sendinblue.com/v3/smtp/email")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("api-key", apiKey)
                .build()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    client.newCall(request).execute()
                } catch (e: Exception) {
                    Log.d("Email error", e.toString())
                }
            }


        }
    }
}