package com.example.hotelzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView

class RezervacijaActivity : AppCompatActivity() {
    private lateinit var datumDolaska: TextView
    private lateinit var datumOdlaska: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rezervacija)

        datumDolaska = findViewById(R.id.dolazak)
        datumOdlaska = findViewById(R.id.odlazak)

        val calendarView = findViewById<CalendarView>(R.id.calendar_view)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$dayOfMonth-${month + 1}-$year"
            datumDolaska.text = "Datum dolaska: $date"
            datumOdlaska.text = "Datum odlaska: $date"
        }
    }
}
