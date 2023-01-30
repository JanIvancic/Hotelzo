package com.example.hotelzo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class PregledSobeActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pregled_sobe)
        val btnBack = findViewById<ImageView>(R.id.back_arrow)
        val cijenaSobe = intent.getIntExtra("cijena_sobe",0)
        val kapacitet = intent.getIntExtra("kapacitet", 0)
        val opisSobe = intent.getStringExtra("opis_sobe")
        val oznaka = intent.getStringExtra("oznaka")
        val roomImage = findViewById<ImageView>(R.id.room_image)
        val imageUrl = intent.getStringExtra("image_url")
        Glide.with(this)
            .load(imageUrl)
            .into(roomImage)

        val tvOznakaSobe = findViewById<TextView>(R.id.tv_oznaka_sobe)
        tvOznakaSobe.text = getString(R.string.oznaka_sobe_pregled) + " " + oznaka

        val tvOpisSobe = findViewById<TextView>(R.id.tv_opis_sobe)
        tvOpisSobe.text = getString(R.string.opis_sobe_pregled) + " " + opisSobe

        val tvCijenaSobe = findViewById<TextView>(R.id.tv_cijena_sobe_value)
        tvCijenaSobe.text = cijenaSobe.toString()

        val tvKapacitet = findViewById<TextView>(R.id.tv_kapacitet_value)
        tvKapacitet.text = kapacitet.toString()

        val gumbRezerviraj = findViewById<Button>(R.id.btn_rezerviraj)
        gumbRezerviraj.setOnClickListener {
            val intent = Intent(this, RezervacijaActivity::class.java)
            intent.putExtra("oznaka", oznaka)
            startActivity(intent)
        }
        btnBack.setOnClickListener{
            finish()
        }
    }
}


