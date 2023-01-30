package com.example.hotelzo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelzo.adapters.ReservationsAdapter
import com.example.hotelzo.data.Reservations
import com.google.firebase.auth.FirebaseAuth
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
    private var gumb:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reservations)

        recyclerView = findViewById(R.id.rv_reservations)
        recyclerView.layoutManager = LinearLayoutManager(this)

        reservationList = arrayListOf()
        currentDate = Calendar.getInstance().time
        db = FirebaseFirestore.getInstance()
        switch = findViewById(R.id.sw_prijasnje)

        getLoggedInUserInfo()

        hideBar()
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            reservationList.clear()
            showActive = !isChecked
            gumb=!isChecked
            getLoggedInUserInfo()
        }


        val btnNewReservation = findViewById<ImageView>(R.id.btnAdd)

        btnNewReservation.setOnClickListener{
            val intent = Intent(this, RezervacijaAdmin::class.java)
            startActivity(intent)
        }
    }

    private fun hideBar() {
        val email = FirebaseAuth.getInstance().currentUser!!.email
        db.collection("Korisnik")
            .whereEqualTo("mail", email)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    val uloga = it.documents[0]["uloga"].toString()
                    val fab=findViewById<View>(R.id.floating_action_button)

                    if(uloga=="admin")
                    {
                        val topBarBack=findViewById<View>(R.id.top_bar_back)
                        topBarBack.visibility=View.GONE

                        fab.setOnClickListener(){
                            logoutUser()
                        }
                    }
                    else
                    {
                        val topBar=findViewById<View>(R.id.top_bar)
                        topBar.visibility=View.GONE
                        val btnBack = findViewById<ImageView>(R.id.back_arrow)
                        fab.visibility=View.GONE
                        val btnAdd=findViewById<ImageView>(R.id.btnAdd)
                        btnAdd.visibility=View.GONE
                        btnBack.setOnClickListener{
                            finish()
                        }

                    }
                }
            }
    }

    fun deleteReservation(id: String?) {
        val dohvaceniId = db.collection("Rezervacija").document(id!!)
        dohvaceniId.delete()
            .addOnSuccessListener {
                Log.d("Uspjesno", "Rezervacija ($id) je uspjesno izbrisana")
            }
            .addOnFailureListener { exception ->
                Log.w("Neuspjesno", "Pogreška kod brisanje rezervacije", exception)
            }
    }

    private fun getReservations(ime: String,uloga:String) {
        if(uloga=="admin")
        {
            if(showActive){
                db.collection("Rezervacija")
                    .whereGreaterThan("datum_kraj", currentDate)
                    .get().addOnSuccessListener {
                        getData(it.documents,uloga)
                    }
            } else {
                db.collection("Rezervacija")
                    .whereLessThan("datum_kraj", currentDate)
                    .get().addOnSuccessListener {
                        getData(it.documents,uloga)
                    }
            }

        }
        else{
            if (showActive) {
                db.collection("Rezervacija")
                    .whereEqualTo("ime", ime)
                    .get()
                    .addOnSuccessListener {
                        val filteredDocs = it.documents.filter { document ->
                            val date = document.get("datum_kraj") as com.google.firebase.Timestamp
                            date.toDate() > currentDate
                        }
                        getData(filteredDocs.toMutableList(),uloga)
                    }


            } else {
                db.collection("Rezervacija")
                    .whereEqualTo("ime", ime)
                    .get().addOnSuccessListener {
                        val filteredDocs = it.documents.filter { document ->
                            val date = document.get("datum_kraj") as com.google.firebase.Timestamp
                            date.toDate() < currentDate
                        }
                        getData(filteredDocs.toMutableList(),uloga)
                    }
            }
        }

    }


    private fun getLoggedInUserInfo() {
        val email = FirebaseAuth.getInstance().currentUser!!.email
        db.collection("Korisnik")
            .whereEqualTo("mail", email)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    val ime = it.documents[0]["ime"].toString()
                    val uloga = it.documents[0]["uloga"].toString()
                    Log.d("IME", "getLoggedInUserInfo: $ime")
                    Log.d("ULOGA", "getLoggedInUserInfo: $uloga")
                    getReservations(ime, uloga)
                }
            }
    }

    private fun logoutUser() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getData(documents: MutableList<DocumentSnapshot>,uloga: String) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")

        for (data in documents){

            var timestamp = data["datum_pocetak"] as com.google.firebase.Timestamp
            val start_date = dateFormat.format(timestamp.toDate())

            timestamp = data["datum_kraj"] as com.google.firebase.Timestamp
            val end_date = dateFormat.format(timestamp.toDate())

            val name:String = data["ime"].toString()
            val room_label:String = data["oznaka"].toString()

            val id : String = data.id

            val reservation = Reservations(end_date = end_date, start_date = start_date, name = name, room_label = room_label, document_id = id)
            reservationList.add(reservation)
        }

        recyclerView.adapter = ReservationsAdapter(reservationList, this,gumb,uloga)
    }
}
