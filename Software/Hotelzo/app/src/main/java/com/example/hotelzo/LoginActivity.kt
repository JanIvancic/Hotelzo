package com.example.hotelzo

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.hotelzo.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar = supportActionBar!!
        actionBar.title = "@string/login_text"

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("@string/pricekajte")
        progressDialog.setMessage("@stgring/prijava_u_tijeku")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //REGISTRACIJA

        binding.btnPrijava.setOnClickListener {
            email = binding.etMail.text.toString().trim()
            password = binding.etLozinka.text.toString().trim()
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etMail.error = "@string/greska"
            }
            else if(TextUtils.isEmpty(password)){
                binding.etLozinka.error = "@string/greska"
            }
            else{
                firebaseLogin()
            }

            }
        }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun firebaseLogin()  {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Prijavljeni ste kao $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Neuspješna prijava", Toast.LENGTH_SHORT).show()
            }
    }
}
