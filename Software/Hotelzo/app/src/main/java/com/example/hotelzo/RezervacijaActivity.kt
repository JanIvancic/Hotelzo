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
            } else if(checkInTimestamp!!.compareTo(checkOutTimestamp!!) > 0) {
                Toast.makeText(
                    this, "Datum odlaska ne moze biti prije datuma dolaska.", Toast.LENGTH_SHORT
                ).show()
            } else {
                addRezarvaciju(checkInTimestamp!!, checkOutTimestamp!!)
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
}