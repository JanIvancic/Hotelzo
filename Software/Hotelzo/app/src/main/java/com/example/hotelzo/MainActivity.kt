package com.example.hotelzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firestore = Firebase.firestore
        val korisnikRef = firestore.collection("Korisnik")
        val korisnikDocs = korisnikRef.get()
        korisnikDocs.addOnSuccessListener { result ->
            for (document in result) {
                Log.d("Korisnik", "${document.id} => ${document.data.get("id_uloge")}")
            }
        }

    }
}