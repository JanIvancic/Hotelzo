package com.example.hotelzo

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.hotelzo.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        checkUser()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(getString(R.string.pricekajte))
        progressDialog.setMessage(getString(R.string.prijava_u_tijeku))
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        val registracija = binding.lblRegistracija
        registracija.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }

        val zablozinka = binding.lblZaboravljenaLozinka
        zablozinka.setOnClickListener {
            startActivity(Intent(this, ForgottenPassword::class.java))
        }

        binding.btnPrijava.setOnClickListener {
            provjeriPodatke()
        }
    }

    private fun provjeriPodatke() {
        email = binding.etMail.text.toString().trim()
        password = binding.etLozinka.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etMail.error = getString(R.string.greska)
        } else if (TextUtils.isEmpty(password)) {
            binding.etLozinka.error = getString(R.string.greska)
        } else {
            firebaseLogin()
        }
    }

    private fun checkUser() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            val email = firebaseUser!!.email

            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("Korisnik")
                .whereEqualTo("mail", email)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val uloga:String = document["uloga"].toString()
                        if (uloga == "admin") {
                            startActivity(Intent(this, AllReservationsActivity::class.java))
                            finish()
                        } else {
                            startActivity(Intent(this, RecyclerViewRoom::class.java))
                            finish()
                        }
                    }
                }
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                val firestore = FirebaseFirestore.getInstance()
                firestore.collection("Korisnik")
                    .whereEqualTo("mail", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val uloga:String = document["uloga"].toString()
                            if (uloga == "admin") {
                                Toast.makeText(this, getString(R.string.prijava_admin), Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, AllReservationsActivity::class.java))
                                finish()
                            } else {
                                startActivity(Intent(this, RecyclerViewRoom::class.java))
                                finish()
                            }
                        }
                    }
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, getString(R.string.prijava_greska), Toast.LENGTH_SHORT).show()
            }
    }
}
