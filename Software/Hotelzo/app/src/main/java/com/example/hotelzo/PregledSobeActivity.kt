package com.example.hotelzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class PregledSobeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pregled_sobe)

        val cijenaSobe = intent.getDoubleExtra("cijenaSobe", 0.0)
        val kapacitet = intent.getIntExtra("kapacitet", 0)
        val opisSobe = intent.getStringExtra("opisSobe")
        val oznakaSobe = intent.getStringExtra("oznakaSobe")

        val tvOznakaSobe = findViewById<TextView>(R.id.tv_oznaka_sobe)
        tvOznakaSobe.text = "Oznaka sobe: $oznakaSobe"

        val tvOpisSobe = findViewById<TextView>(R.id.tv_opis_sobe)
        tvOpisSobe.text = "Opis sobe: $opisSobe"


        val tvCijenaSobe = findViewById<TextView>(R.id.tv_cijena_sobe_value)
        tvCijenaSobe.text = cijenaSobe.toString()

        val tvKapacitet = findViewById<TextView>(R.id.tv_kapacitet_value)
        tvKapacitet.text = kapacitet.toString()

        val gumbRezerviraj = findViewById<Button>(R.id.btn_rezerviraj)
        gumbRezerviraj.setOnClickListener {
            //započni rezervaciju
        }
    }


}


