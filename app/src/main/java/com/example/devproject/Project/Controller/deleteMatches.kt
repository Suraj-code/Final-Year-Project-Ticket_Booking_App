package com.example.devproject.Project.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.devproject.Project.Model.Database
import com.example.devproject.R

class deleteMatches : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_matches)

        val mId = findViewById<EditText>(R.id.matchIdText)
        val delete = findViewById<Button>(R.id.deleteBtn)
        val back = findViewById<Button>(R.id.deleteBackBtn)

        val myDataBase = Database(this)

        back.setOnClickListener {
            val intent = Intent(this, AdminActivity2::class.java)
            startActivity(intent)
            finish()
        }

        //deletes the match that is associated with the id entered
        delete.setOnClickListener {
            if(myDataBase.deleteMatch(mId.text.toString())){
                Toast.makeText(this, "Match deleted!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}