package com.example.hotelzo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class ForgottenPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotten_password)
        val btnForgottenPassword = findViewById<Button>(R.id.btn_submit_forgotten_password)
        val btnBack = findViewById<ImageView>(R.id.back_arrow)

        btnBack.setOnClickListener{
            finish()
        }

     btnForgottenPassword.setOnClickListener{
            val email= findViewById<EditText>(R.id.et_email).text.toString()
            if(email.isEmpty()){
                Toast.makeText(this,
                    getString(R.string.toast_empty_mail), Toast.LENGTH_SHORT).show()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnSuccessListener(this){
                            Toast.makeText(this,
                                getString(R.string.toast_existing_mail_forgotten_password), Toast.LENGTH_SHORT).show()
                            finish()
                    }.addOnFailureListener(this){
                        Toast.makeText(this,
                            getString(R.string.toast_non_existing_mail_forgotten_password), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }


