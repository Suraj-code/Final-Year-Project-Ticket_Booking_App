package com.example.devproject.Project.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.devproject.R

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        val lgnAdminBtn = findViewById<Button>(R.id.AdminLoginButton)
        val back = findViewById<Button>(R.id.loginBackButton)
        val passwordText = findViewById<EditText>(R.id.editTextPassword)

        //checks if the admin has entered the correct password
        lgnAdminBtn.setOnClickListener {
            if(passwordText.text.toString() == "Password1234"){
                val intent = Intent(this, AdminActivity2::class.java)
                startActivity(intent)
                finish()
            }else {
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
            }
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}