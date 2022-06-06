package com.example.devproject.Project.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.devproject.Project.Model.Database
import com.example.devproject.Project.Model.User
import com.example.devproject.R
import com.google.android.material.navigation.NavigationView

class RegisterPage : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        val drawerLayout : DrawerLayout = findViewById(R.id.navBar3)
        val nav : NavigationView = findViewById(R.id.navView3)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //menu bar items -> takes you to the item you click on
        nav.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.navHome -> {
                    val intent = Intent(this, MatchesPage::class.java)
                    startActivity(intent)
                    finish()
                }

                R.id.navRegister -> {
                    val intent = Intent(this, RegisterPage::class.java)
                    startActivity(intent)
                    finish()
                }

                R.id.navAdmin -> {
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                R.id.navLogin -> {
                    val intent = Intent(this, LoginPage::class.java)
                    startActivity(intent)
                    finish()
                }

                R.id.navLogout -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

            true


        }

        val bButton = findViewById<Button>(R.id.Bbutton)
        val rButton = findViewById<Button>(R.id.RegisterButton2)

        val name = findViewById<EditText>(R.id.NameText)
        val surname = findViewById<EditText>(R.id.SurNameText)
        val email = findViewById<EditText>(R.id.EmailText)
        val password = findViewById<EditText>(R.id.PasswordText)
        val Cpassword = findViewById<EditText>(R.id.ConfirmPasswordText)
        val mobile = findViewById<EditText>(R.id.MobileText)
        val gender = findViewById<EditText>(R.id.GenderText)


        bButton.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //validation for email incase wrong format entered
        email.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
                    rButton.isEnabled = true
                }else {
                    rButton.isEnabled = false
                    email.setError("Invalid Email")
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        //validates if the password entered is the same as the one entered above when registering
        Cpassword.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(Cpassword.text.toString() != password.text.toString()){
                    Cpassword.setError("Passwords do not match!")
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        //Validate that all fields are completed and adds the user to the database and account is created
        rButton.setOnClickListener {

            if(name.text.toString() == "" || surname.text.toString() == "" || email.text.toString() == "" || password.text.toString() == "" || Cpassword.text.toString() == "" ||
                mobile.text.toString() == "" || gender.text.toString() == ""){

                Toast.makeText(this, "Please fill in the required fields", Toast.LENGTH_SHORT).show()

            }else {

                val newUser = User(
                    name.text.toString(),
                    password.text.toString(),
                    email.text.toString(),
                    mobile.text.toString(),
                    gender.text.toString()
                )
                val mydatabase = Database(this)
                val result = mydatabase.addUser(newUser)
                when (result) {
                    1 -> {
                        Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                    }
                    -1 -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    -2 -> Toast.makeText(this, "Cannot open database", Toast.LENGTH_SHORT).show()
                    -3 -> Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}