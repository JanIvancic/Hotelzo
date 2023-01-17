package com.example.hotelzo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, PregledSobeActivity::class.java)
        intent.putExtra("cijenaSobe", 150.0)
        intent.putExtra("kapacitet", 2)
        intent.putExtra("opisSobe", "Dvokrevetna soba s pogledom na more nudi luksuzni boravak za dvije osobe. Soba ima prostranu terasu s pogledom na more, moderan interijer, klimatiziranje, televiziju. U sobi se nalazi i minibar sa pićima te kupaonica s tušem i sušilom za kosu. Ovaj smještaj je idealan za parove koji žele uživati u romantičnom boravku sa pogledom na more.")
        intent.putExtra("oznakaSobe", "Soba 4")
        startActivity(intent)



    }
}