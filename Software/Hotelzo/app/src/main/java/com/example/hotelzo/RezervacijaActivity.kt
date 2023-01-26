package com.example.hotelzo

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.String.format
import java.text.SimpleDateFormat
import com.google.firebase.Timestamp
import java.util.*

class RezervacijaActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var checkInDateTextView: TextView
    private lateinit var checkOutDateTextView: TextView
    private var checkInTimestamp: Timestamp? = null
    private var checkOutTimestamp: Timestamp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rezervacija)
        db = FirebaseFirestore.getInstance()

        checkInDateTextView = findViewById(R.id.tv_check_in_date)
        checkOutDateTextView = findViewById(R.id.tv_check_out_date)
        val removePrvi = findViewById<TextView>(R.id.removePrvi)
        val removeDrugi = findViewById<TextView>(R.id.removeDrugi)
        val calendarView = findViewById<CalendarView>(R.id.calendar_view)

        setUpReservationButton()

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

    private fun setUpReservationButton() {
        val btn_rezerviraj = findViewById<Button>(R.id.btn_rezerviraj)
        btn_rezerviraj.setOnClickListener {
            if (checkInTimestamp == null || checkOutTimestamp == null) {
                if (checkInTimestamp == null && checkOutTimestamp == null) {
                    Toast.makeText(
                        this, "Molim vas izaberite datum dolaska i odlaska.", Toast.LENGTH_SHORT
                    ).show()
                } else if (checkInTimestamp == null) {
                    Toast.makeText(
                        this, "Molim vas izaberite datum dolaska.", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this, "Molim vas izaberite datum odlaska.", Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                checkForReservationConflict(checkInTimestamp!!, checkOutTimestamp!!)
            }
        }

    }

    private fun addRezarvaciju(checkInTimestamp: Timestamp, checkOutTimestamp: Timestamp) {
        val dbReference = db.collection("Rezervacija")
        val newRezervacija = hashMapOf(
            "datum_kraj" to checkOutTimestamp,
            "datum_pocetak" to checkInTimestamp
        )
        dbReference.add(newRezervacija)
            .addOnSuccessListener {
                Toast.makeText(this, "Rezervacija uspješno kreirana", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Greška: $e", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkForReservationConflict(checkInTimestamp: Timestamp, checkOutTimestamp: Timestamp) {
        var conflictFound = false
        db.collection("Rezervacija")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val existingCheckInTimestamp = document.getTimestamp("datum_pocetak")
                    val existingCheckOutTimestamp = document.getTimestamp("datum_kraj")
                    if (checkInTimestamp.toDate().after(existingCheckInTimestamp!!.toDate()) && checkInTimestamp.toDate().before(existingCheckOutTimestamp!!.toDate()) ||
                        checkOutTimestamp.toDate().after(existingCheckInTimestamp.toDate()) && checkOutTimestamp.toDate().before(existingCheckInTimestamp?.toDate()) ||
                        checkInTimestamp.toDate().equals(existingCheckInTimestamp.toDate()) || checkOutTimestamp.toDate().equals(existingCheckOutTimestamp?.toDate())) {
                        Toast.makeText(
                            this, "Već postoji rezervacija za odabrani datum. Molimo odaberite drugi datum.", Toast.LENGTH_SHORT
                        ).show()
                        conflictFound = true
                        break
                    }
                }
                if (!conflictFound) {
                    addRezarvaciju(checkInTimestamp, checkOutTimestamp)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Pogreška pri dohvaćanju postojećih rezervacija. Molimo pokušajte ponovno.", Toast.LENGTH_SHORT).show()
            }
    }






}