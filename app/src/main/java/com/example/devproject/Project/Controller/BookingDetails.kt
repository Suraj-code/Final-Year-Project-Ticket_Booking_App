package com.example.devproject.Project.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.devproject.Project.Model.Database
import com.example.devproject.R
import com.google.android.material.navigation.NavigationView

class BookingDetails : AppCompatActivity() {

    var name : String?? = null
    var d : String?? = null
    var homeTeam : String?? = null
    var awayTeam : String?? = null
    var index = 0

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)


        //menu bar layout
        val drawerLayout : DrawerLayout = findViewById(R.id.navBar5)
        val nav : NavigationView = findViewById(R.id.navView5)

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

        val nameText = findViewById<TextView>(R.id.nameText)
        val dateText = findViewById<TextView>(R.id.bookingDateTV)
        val home = findViewById<TextView>(R.id.bookingHomeText)
        val away = findViewById<TextView>(R.id.bookingAwayText)
        val blockTV = findViewById<TextView>(R.id.blockTV)
        val seatR = findViewById<TextView>(R.id.seatRowTV)
        val seatNo = findViewById<TextView>(R.id.seatTV)
        val bookingId = findViewById<TextView>(R.id.bookingTV)
        val priceTv = findViewById<TextView>(R.id.bPriceTV)

        name = intent.getStringExtra("name")
        d = intent.getStringExtra("d")
        homeTeam = intent.getStringExtra("homeTeam")
        awayTeam = intent.getStringExtra("awayTeam")

        val myDataBase = Database(this)

        //display the details for booking page
        for(num in 0 until myDataBase.getAllBookingDetails().size) {

            nameText.text = myDataBase.getAllBookingDetails()
                .map { it.Name }[num].toString()
            dateText.text = myDataBase.getAllBookingDetails()
                .map { it.MatchDate }[num].toString()
            blockTV.text = myDataBase.getAllBookingDetails()
                .map { it.Block }[num].toString()
            seatR.text = myDataBase.getAllBookingDetails()
                .map { it.SeatRow }[num].toString()
            seatNo.text = myDataBase.getAllBookingDetails()
                .map { it.Seat }[num].toString()
            bookingId.text = "000" + myDataBase.getAllBookingDetails()
                .map { it.BookingId }[num].toString()
            priceTv.text = myDataBase.getAllBookingDetails()
                .map { it.Price }[num].toString()
            home.text = homeTeam
            away.text = awayTeam
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}