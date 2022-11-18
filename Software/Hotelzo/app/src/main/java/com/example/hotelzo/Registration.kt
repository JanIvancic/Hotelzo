package com.example.hotelzo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern

class Registration : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val btnRegister = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener {
            RegisterUser()
        }
    }


    private fun RegisterUser(){

        val email = findViewById<EditText>(R.id.et_email_input).text.toString()
        val password = findViewById<EditText>(R.id.et_password_input).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.et_confirm_password_input).text.toString()
        val phoneNumber = findViewById<EditText>(R.id.et_phone_number_input).text.toString()
        val name = findViewById<EditText>(R.id.et_name_input).text.toString()

        if(email.isBlank() || password.isBlank() || confirmPassword.isBlank() || phoneNumber.isBlank() || name.isBlank()){
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

        val phone_number_test: Pattern = Pattern.compile("^[+]?[0-9]{9,13}\$")
        if(!phone_number_test.matcher(phoneNumber).matches()){
            Toast.makeText(this, getString(R.string.register_invalid_phone_number), Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Ispravni podaci.", Toast.LENGTH_SHORT).show()
    }

}