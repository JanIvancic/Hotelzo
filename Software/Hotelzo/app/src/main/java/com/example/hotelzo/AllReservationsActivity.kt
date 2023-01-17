package com.example.hotelzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelzo.adapters.ReservationsAdapter
import com.example.hotelzo.data.Reservations
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class AllReservationsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var reservationList: ArrayList<Reservations>
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reservations)

        recyclerView = findViewById(R.id.rv_reservations)
        recyclerView.layoutManager = LinearLayoutManager(this)

        reservationList = arrayListOf()

        val dateFormat = SimpleDateFormat("dd.MM.yyyy")

        db = FirebaseFirestore.getInstance()
        db.collection("Rezervacija").get().addOnSuccessListener {

            for (data in it.documents){

                var timestamp = data["datum_pocetak"] as com.google.firebase.Timestamp
                val start_date = dateFormat.format(timestamp.toDate())

                timestamp = data["datum_kraj"] as com.google.firebase.Timestamp
                val end_date = dateFormat.format(timestamp.toDate())

                val name:String = data["ime"].toString()
                val room_label:String = data["oznaka_sobe"].toString()

                val reservation = Reservations(end_date = end_date, start_date = start_date, name = name, room_label = room_label)
                reservationList.add(reservation)

            }
            recyclerView.adapter = ReservationsAdapter(reservationList)
        }
    }
}