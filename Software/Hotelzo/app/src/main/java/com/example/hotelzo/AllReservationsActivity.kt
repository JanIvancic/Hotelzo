package com.example.hotelzo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelzo.adapters.ReservationsAdapter
import com.example.hotelzo.data.Reservations
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.collections.ArrayList

class AllReservationsActivity : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switch: Switch
    private lateinit var recyclerView: RecyclerView

    private lateinit var reservationList: ArrayList<Reservations>
    private lateinit var db: FirebaseFirestore

    private var showActive: Boolean = true
    private lateinit var currentDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reservations)

        recyclerView = findViewById(R.id.rv_reservations)
        recyclerView.layoutManager = LinearLayoutManager(this)

        reservationList = arrayListOf()
        currentDate = Calendar.getInstance().time
        db = FirebaseFirestore.getInstance()
        switch = findViewById(R.id.sw_prijasnje)

        getReservations()

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            reservationList.clear()
            showActive = !isChecked
            getReservations()
        }

    }

    private fun getReservations(){
        if(showActive){
            db.collection("Rezervacija")
                .whereGreaterThan("datum_kraj", currentDate)
                .get().addOnSuccessListener {
                    getData(it.documents)
                }
        } else {
            db.collection("Rezervacija")
                .whereLessThan("datum_kraj", currentDate)
                .get().addOnSuccessListener {
                    getData(it.documents)
                }
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun getData(documents: MutableList<DocumentSnapshot>) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")

        for (data in documents){

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
