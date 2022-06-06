package com.example.devproject.Project.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.devproject.Project.Model.Database
import com.example.devproject.Project.Model.User
import com.example.devproject.R
import com.google.android.material.navigation.NavigationView

class LoginPage : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val drawerLayout : DrawerLayout = findViewById(R.id.navBar6)
        val nav : NavigationView = findViewById(R.id.navView6)

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

        val backButton = findViewById<Button>(R.id.backButton)
        val login = findViewById<Button>(R.id.lgbButton1)
        val lgnEmail = findViewById<EditText>(R.id.EditTextEmail)
        val lgnPassword = findViewById<EditText>(R.id.EditTextPassword)

        val user = findViewById<TextView>(R.id.userName)
        val email = findViewById<TextView>(R.id.email)


        backButton.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        //validation for email
        lgnEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(lgnEmail.text.toString()).matches()){
                    login.isEnabled = true
                }else {
                    login.isEnabled = false
                    lgnEmail.setError("Invalid Email")
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        //validation for login page
        login.setOnClickListener {

            if(lgnEmail.text.toString().isEmpty() || lgnPassword.text.toString().isEmpty())
                Toast.makeText(this,"Please insert Username and Password", Toast.LENGTH_LONG).show()
            else {

                val myDataBase = Database(this)
                val result = myDataBase.getUser(User("", lgnPassword.text.toString(), lgnEmail.text.toString(), "", ""))
                if( result == -1) {
                    Toast.makeText(this, "User not found, please try again", Toast.LENGTH_SHORT)
                        .show()
                }else if( result == -2) {
                    Toast.makeText(this, "Cannot open database", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MatchesPage::class.java)
                    startActivity(intent)
                    finish()

                }

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