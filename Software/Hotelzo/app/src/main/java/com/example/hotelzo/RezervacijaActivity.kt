package com.example.hotelzo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelzo.roomViewer.RecyclerViewRoom
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.String.format
import java.text.SimpleDateFormat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.*

class RezervacijaActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var checkInDateTextView: TextView
    private lateinit var checkOutDateTextView: TextView
    private var checkInTimestamp: Timestamp? = null
    private var checkOutTimestamp: Timestamp? = null
    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rezervacija)
        db = FirebaseFirestore.getInstance()

        checkInDateTextView = findViewById(R.id.tv_check_in_date)
        checkOutDateTextView = findViewById(R.id.tv_check_out_date)
        val removePrvi = findViewById<TextView>(R.id.removePrvi)
        val removeDrugi = findViewById<TextView>(R.id.removeDrugi)
        val calendarView = findViewById<CalendarView>(R.id.calendar_view)
        val oznaka = intent.getStringExtra(getString(R.string.oznaka))

        if (oznaka != null) {
            setUpReservationButton(oznaka)
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val selectedTimestamp = Timestamp(selectedDate.time)
            if (checkInTimestamp == null) {
                checkInTimestamp = selectedTimestamp
                checkInDateTextView.text = SimpleDateFormat("dd/MM/yyyy").format(selectedTimestamp.toDate())
            } else {
                checkOutTimestamp = selectedTimestamp
                checkOutDateTextView
                    .text = SimpleDateFormat("dd/MM/yyyy").format(selectedTimestamp.toDate())
            }
        }

        removePrvi.setOnClickListener {
            checkInTimestamp = null
            checkInDateTextView.text = ""
        }
        removeDrugi.setOnClickListener {
            checkOutTimestamp = null
            checkOutDateTextView.text = ""
        }
    }

    private fun setUpReservationButton(oznaka: String) {
        val btn_rezerviraj = findViewById<Button>(R.id.btn_rezerviraj)
        val ime = userName
        btn_rezerviraj.setOnClickListener {
            if (checkInTimestamp == null || checkOutTimestamp == null) {
                if (checkInTimestamp == null && checkOutTimestamp == null) {
                    Toast.makeText(
                        this, getString(R.string.odabir_datuma), Toast.LENGTH_SHORT
                    ).show()
                } else if (checkInTimestamp == null) {
                    Toast.makeText(
                        this, getString(R.string.odabir_dolaska), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this, getString(R.string.odabir_odlaska), Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                checkForReservationConflict(checkInTimestamp!!, checkOutTimestamp!!,oznaka)
            }
        }

    }

    private fun addRezarvaciju(checkInTimestamp: Timestamp, checkOutTimestamp: Timestamp, ime: String, oznaka: String) {
        val dbReference = db.collection("Rezervacija")
        val newRezervacija = hashMapOf(
            "datum_kraj" to checkOutTimestamp,
            "datum_pocetak" to checkInTimestamp,
            "ime" to ime,
            "oznaka" to oznaka
        )
        dbReference.add(newRezervacija)
            .addOnSuccessListener {
                Toast.makeText(this, getString(R.string.uspjesna_rez), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RecyclerViewRoom::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, getString(R.string.greska), Toast.LENGTH_SHORT).show()
            }
    }


    private fun getCurrentUserName(callback: (String) -> Unit) {
        val email = FirebaseAuth.getInstance().currentUser!!.email
        var ime: String = ""

        db.collection("Korisnik")
            .whereEqualTo("mail", email)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    ime = it.documents[0]["ime"].toString()
                    callback(ime)
                }
            }
    }

    private fun checkForReservationConflict(checkInTimestamp: Timestamp, checkOutTimestamp: Timestamp, oznaka: String) {
        var conflictFound = false
        getCurrentUserName { name ->
            if (checkInTimestamp.toDate().before(Date())) {
                Toast.makeText(this, getString(R.string.greska_dolazak_trenutni_datum), Toast.LENGTH_SHORT).show()
                conflictFound = true
            } else if (checkOutTimestamp.toDate().before(checkInTimestamp.toDate())) {
                Toast.makeText(this, getString(R.string.greska_odlazak_dolazak), Toast.LENGTH_SHORT).show()
                conflictFound = true
            }

            if (!conflictFound) {
                db.collection("Rezervacija")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            if (document.getString("oznaka") != oznaka) {
                                continue
                            }
                            val existingCheckInTimestamp = document.getTimestamp("datum_pocetak")
                            val existingCheckOutTimestamp = document.getTimestamp("datum_kraj")
                            if ((checkInTimestamp.toDate().after(existingCheckInTimestamp!!.toDate()) && checkInTimestamp.toDate().before(existingCheckOutTimestamp!!.toDate())) ||
                                (checkOutTimestamp.toDate().after(existingCheckInTimestamp.toDate()) && checkOutTimestamp.toDate().before(existingCheckOutTimestamp!!.toDate())) ||
                                (checkInTimestamp.toDate().before(existingCheckOutTimestamp!!.toDate()) && checkOutTimestamp.toDate().after(existingCheckInTimestamp.toDate()))) {
                                Toast.makeText(
                                    this, getString(R.string.posotji_rezervacija_datum), Toast.LENGTH_SHORT
                                ).show()
                                conflictFound = true
                                break
                            }
                        }
                        if (!conflictFound) {
                            addRezarvaciju(checkInTimestamp, checkOutTimestamp, name, oznaka)
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, getString(R.string.greska_postojecih_rezervacija), Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}