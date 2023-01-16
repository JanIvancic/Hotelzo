package com.example.hotelzo.roomViewer

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelzo.R
import com.google.firebase.firestore.FirebaseFirestore


class RecyclerViewRoom : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var roomList: ArrayList<Room>
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_list)
        val btnBack = findViewById<ImageView>(R.id.back_arrow)

        btnBack.setOnClickListener{
            finish()
        }
        recyclerView = findViewById(R.id.recycler_view_rooms)
        recyclerView.layoutManager = LinearLayoutManager(this)

        roomList = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db.collection("Soba").get().addOnSuccessListener {
            if(!it.isEmpty){
                for (data in it.documents){
                    val room: Room? = data.toObject(Room::class.java)
                    if (room != null) {
                        roomList.add(room)

                    }
                }
                Log.d("RecyclerViewRoom", "Data loaded successfully")
                recyclerView.adapter = RoomAdapter(roomList)
            }
        }
            .addOnFailureListener {
                Toast.makeText( this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}