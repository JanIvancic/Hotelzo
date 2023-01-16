package com.example.hotelzo.roomViewer

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelzo.R
import com.google.firebase.ktx.Firebase

class RecyclerViewRoom : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var roomList: ArrayList<Room>
  

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_list)
    }
}