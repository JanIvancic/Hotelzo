package com.example.hotelzo

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class RezervacijaActivity : AppCompatActivity() {

    private lateinit var checkInDateTextView: TextView
    private lateinit var checkOutDateTextView: TextView
    private var checkInDate: Long = 0
    private var checkOutDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rezervacija)

        checkInDateTextView = findViewById(R.id.tv_check_in_date)
        checkOutDateTextView = findViewById(R.id.tv_check_out_date)
        val removePrvi = findViewById<TextView>(R.id.removePrvi)
        val removeDrugi = findViewById<TextView>(R.id.removeDrugi)
        val calendarView = findViewById<CalendarView>(R.id.calendar_view)

        setUpReservationButton();

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            //setUpReservationButton();

            if (checkInDate == 0L) {
                checkInDate = selectedDate.timeInMillis
                checkInDateTextView.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)
            } else {
                checkOutDate = selectedDate.timeInMillis
                checkOutDateTextView.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)
            }
        }

        removePrvi.setOnClickListener {
            checkInDate = 0
            checkInDateTextView.setText("")
        }
        removeDrugi.setOnClickListener {
            checkOutDate = 0
            checkOutDateTextView.setText("")
        }
    }
    private fun setUpReservationButton() {
        val btn_rezerviraj = findViewById<Button>(R.id.btn_rezerviraj)
        btn_rezerviraj.setOnClickListener {
            if (checkInDate != 0L && checkOutDate != 0L) {
                val checkIn = Calendar.getInstance().apply { timeInMillis = checkInDate }
                val checkOut = Calendar.getInstance().apply { timeInMillis = checkOutDate }
                if (checkOut.before(checkIn)) {
                    Toast.makeText(this, "Datum odlaska ne može biti prije datuma dolaska.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                // Perform the reservation action
            } else {
                // Display message to user that check-in and check-out dates are required
                Toast.makeText(this, "Odaberite datum dolaska i odlaska.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
