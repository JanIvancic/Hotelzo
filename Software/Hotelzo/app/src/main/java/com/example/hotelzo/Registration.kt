package com.example.hotelzo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class Registration : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private lateinit var phoneNumber: String
    private lateinit var name: String
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        val btnRegister = findViewById<Button>(R.id.btn_register)
        val btnBack = findViewById<ImageView>(R.id.back_arrow)
        btnRegister.setOnClickListener {
            CheckInput()
        }

        btnBack.setOnClickListener{
            finish()
        }
    }


    private fun CheckInput(){

        email = findViewById<EditText>(R.id.et_email_input).text.toString()
        password = findViewById<EditText>(R.id.et_password_input).text.toString()
        confirmPassword = findViewById<EditText>(R.id.et_confirm_password_input).text.toString()
        phoneNumber = findViewById<EditText>(R.id.et_phone_number_input).text.toString()
        name = findViewById<EditText>(R.id.et_name_input).text.toString()
        username = findViewById<EditText>(R.id.et_username_input).text.toString()

        if(email.isBlank() || password.isBlank() || confirmPassword.isBlank() || phoneNumber.isBlank() || name.isBlank() || username.isBlank()){
            Toast.makeText(this, getString(R.string.register_empty_field), Toast.LENGTH_SHORT).show()
            return
        }

        if(password.length < 8){
            Toast.makeText(this, getString(R.string.register_short_password), Toast.LENGTH_LONG).show()
            return
        }

        if(password != confirmPassword){
            Toast.makeText(this, getString(R.string.register_passwords_mismatch), Toast.LENGTH_SHORT).show()
            return
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, getString(R.string.register_invalid_email), Toast.LENGTH_SHORT).show()
            return
        }

        val phoneNumberTest: Pattern = Pattern.compile("^[+]?[0-9]{9,13}\$")
        if(!phoneNumberTest.matcher(phoneNumber).matches()){
            Toast.makeText(this, getString(R.string.register_invalid_phone_number), Toast.LENGTH_SHORT).show()
            return
        }

        RegisterUserAuth()
    }

    private fun RegisterUserAuth() {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    AddUserToDatabase()
                    startActivity(Intent(this,MainActivity::class.java))
                } else {
                    Toast.makeText(baseContext, getString(R.string.register_error), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun AddUserToDatabase(){
        val dbReference = db.collection("Korisnik")
        val newUser = hashMapOf(
            "broj_telefona" to phoneNumber,
            "ime" to name,
            "lozinka" to password,
            "mail" to email,
            "korisnicko_ime" to username,
            "uloga" to getString(R.string.guest_role)
        )
        dbReference.add(newUser)
    }


}