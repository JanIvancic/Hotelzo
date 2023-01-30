package com.example.hotelzo


import android.content.Context
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
        private val apiKey = "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"

        fun sendCancelationEmail(context: Context, email: String, name: String,  dateStart: String, dateEnd: String, room: String){
            val message = context.getString(R.string.email_greeting) + " " +  name +  "<br>" +
                    context.getString(R.string.your_reservation) + " " + room + " " +
                    context.getString(R.string.from) + " " + dateStart + " " +
                    context.getString(R.string.to) + " " + dateEnd + " " + context.getString(R.string.cancelled) + "<br>" +
                    context.getString(R.string.email_farewell) + "<br>" +
                    context.getString(R.string.app_name)

            val subject = context.getString(R.string.email_subject_cancelled)

            this.sendEmail(message, subject, email, name)
        }

        fun sendReservationEmail(context: Context, email:String, name:String, dateStart: String, dateEnd: String, room:String){
            val message = context.getString(R.string.email_greeting) + " " +  name +  "<br>" +
                    context.getString(R.string.email_room_reserved) + " " +  room + "<br>" +
                    context.getString(R.string.email_reservation_start) + " " + dateStart + "<br>" +
                    context.getString(R.string.email_reservation_end) + " " + dateEnd + "<br>" +
                    context.getString(R.string.email_farewell) + "<br>" +
                    context.getString(R.string.app_name)

            val subject = context.getString(R.string.email_subject_reservation)

            this.sendEmail(message, subject, email, name)
        }

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