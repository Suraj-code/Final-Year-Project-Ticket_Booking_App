package com.example.devproject.Project.Controller

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.devproject.Project.Model.Booking
import com.example.devproject.Project.Model.Database
import com.example.devproject.R
import com.google.android.material.navigation.NavigationView
import java.util.*

class MatchDetails : AppCompatActivity() {

    var homeTeam : String?? = null
    var homeTeamImage : ByteArray?? = null
    var awayTeam : String?? = null
    var awayTeamImage : ByteArray?? = null
    var stadiumName : String?? = null
    var dateAndTime : String?? = null
    var p: Int?? = 0

    var name : String?? = null
    var d : String?? = null

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_details)

        val drawerLayout : DrawerLayout = findViewById(R.id.navBar4)
        val nav : NavigationView = findViewById(R.id.navView4)

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

        homeTeam = intent.getStringExtra("homeTeam")
        homeTeamImage = intent.getByteArrayExtra("homeTeamImage")
        awayTeam = intent.getStringExtra("awayTeam")
        awayTeamImage = intent.getByteArrayExtra("awayTeamImage")
        stadiumName = intent.getStringExtra("stadiumName")
        dateAndTime = intent.getStringExtra("dateAndTime")


        val book = findViewById<Button>(R.id.bookingBtn)
        val homeImage = findViewById<ImageView>(R.id.hTeamImg)
        val awayImage = findViewById<ImageView>(R.id.aTeamImg)
        val home = findViewById<TextView>(R.id.homeTV)
        val away = findViewById<TextView>(R.id.awayTV)
        val stadium = findViewById<TextView>(R.id.stdText)
        val dat = findViewById<TextView>(R.id.datText)
        val bookingBlock = findViewById<Spinner>(R.id.blockSpinner)
        val bookingRow = findViewById<Spinner>(R.id.rowSpinner)
        val bookingSeat = findViewById<Spinner>(R.id.seatSpinner)


        val name = findViewById<EditText>(R.id.editTextName)

        home.text = homeTeam
        away.text = awayTeam
        stadium.text = stadiumName
        dat.text = dateAndTime

        val myDataBase = Database(this)

        //gets seatNo, SeatRow and Block from the database
        myDataBase.getAllSeats().map { it.seatNo }
        myDataBase.getAllSeats().map { it.SeatRow }
        myDataBase.getAllSeats().map { it.Block}

        //gets the home and away team image from the database that corresponds to the home and away team on the page
        val hImage: ByteArray = myDataBase.getAllMatches().filter { it.HomeTeam == home.text.toString() }.map { it.HomeTeamImg }[0]
        val bmp: Bitmap = BitmapFactory.decodeByteArray(hImage, 0, hImage.size)
        val AImage: ByteArray = myDataBase.getAllMatches().filter { it.AwayTeam == away.text.toString() }.map { it.AwayTeamImg }[0]
        val bmp2: Bitmap = BitmapFactory.decodeByteArray(AImage, 0, AImage.size)

        val hTeamI = findViewById<ImageView>(R.id.hTeamImg).setImageBitmap(bmp)
        val aTeamI = findViewById<ImageView>(R.id.aTeamImg).setImageBitmap(bmp2)

        //gets all the seat numbers for seat row A
        val arrayList: ArrayList<Int> = ArrayList()
        for(num in 0 until myDataBase.getAllSeats().filter { it.SeatRow == "A" }.map { it.seatNo }.size){
            arrayList.add(myDataBase.getAllSeats().filter { it.SeatRow == "A" }.map { it.seatNo }.distinct()[num])
        }

        //adds the seat numbers of seat row A in the dropdown menu
        val seatNumberA: ArrayAdapter<Int> =
            ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, arrayList)
        seatNumberA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //gets all the seat numbers for seat row B
        val arrayListB: ArrayList<Int> = ArrayList()
        for(num in 0 until myDataBase.getAllSeats().filter { it.SeatRow == "B" }.map { it.seatNo }.size){
            arrayListB.add(myDataBase.getAllSeats().filter { it.SeatRow == "B" }.map { it.seatNo }.distinct()[num])
        }

        //adds the seat numbers of seat row B in the dropdown menu
        val seatNumberB: ArrayAdapter<Int> =
            ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, arrayListB)
        seatNumberB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bookingSeat.setAdapter(seatNumberB)

        //gets all the seat numbers for seat row C
        val arrayListC: ArrayList<Int> = ArrayList()
        for(num in 0 until myDataBase.getAllSeats().filter { it.SeatRow == "C" }.map { it.seatNo }.size) {
            arrayListC.add(myDataBase.getAllSeats().filter { it.SeatRow == "C" }.map { it.seatNo }.distinct()[num])
        }

        //adds the seat numbers of seat row C in the dropdown menu
        val seatNumberC: ArrayAdapter<Int> =
            ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, arrayListC)
        seatNumberC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bookingSeat.setAdapter(seatNumberC)

        //gets all the seat numbers for seat row D
        val arrayListD: ArrayList<Int> = ArrayList()
        for(num in 0 until myDataBase.getAllSeats().filter { it.SeatRow == "D" }.map { it.seatNo }.size){
            arrayListD.add(myDataBase.getAllSeats().filter { it.SeatRow == "D" }.map { it.seatNo }.distinct()[num])
        }

        //adds the seat numbers of seat row D in the dropdown menu
        val seatNumberD: ArrayAdapter<Int> =
            ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, arrayListD)
        seatNumberD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bookingSeat.setAdapter(seatNumberD)

        //gets all the seat numbers for seat row E
        val arrayListE: ArrayList<Int> = ArrayList()
        for(num in 0 until myDataBase.getAllSeats().filter { it.SeatRow == "E" }.map { it.seatNo }.size){
            arrayListE.add(myDataBase.getAllSeats().filter { it.SeatRow == "E" }.map { it.seatNo }.distinct()[num])
        }

        //adds the seat numbers of seat row E in the dropdown menu
        val seatNumberE: ArrayAdapter<Int> =
            ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, arrayListE)
        seatNumberE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bookingSeat.setAdapter(seatNumberE)

        //gets all the seat numbers for seat row F
        val arrayListF: ArrayList<Int> = ArrayList()
        for(num in 0 until myDataBase.getAllSeats().filter { it.SeatRow == "F" }.map { it.seatNo }.size){
            arrayListF.add(myDataBase.getAllSeats().filter { it.SeatRow == "F" }.map { it.seatNo }.distinct()[num])
        }

        //adds the seat numbers of seat row F in the dropdown menu
        val seatNumberF: ArrayAdapter<Int> =
            ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, arrayListF)
        seatNumberF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bookingSeat.setAdapter(seatNumberF)

        //gets all the blocks from the database
        val arrayList3: ArrayList<String> = ArrayList()
        for(num in 0..1){
            arrayList3.add(myDataBase.getAllBlocks().map { it.Block }[num].toString())
        }

        //adds the blocks into the dropdown menu
        val blockk: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList3)
        blockk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bookingBlock.setAdapter(blockk)

        //gets the seat row that is associated with block A
        val arrayList4: ArrayList<String> = ArrayList()
        arrayList4.add(myDataBase.getAllSeats().filter { it.Block == "BlockA" }.map { it.SeatRow }.distinct()[0].toString())
        arrayList4.add(myDataBase.getAllSeats().filter { it.Block == "BlockA" }.map { it.SeatRow }.distinct()[1].toString())
        arrayList4.add(myDataBase.getAllSeats().filter { it.Block == "BlockA" }.map { it.SeatRow }.distinct()[2].toString())

        //adds the seat rows to the dropdown menu
        val sRow: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList4)
        sRow.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //gets the seat row that is associated with block B
        val arrayList5: ArrayList<String> = ArrayList()
        arrayList5.add(myDataBase.getAllSeats().filter { it.Block == "BlockB" }.map { it.SeatRow }.distinct()[0].toString())
        arrayList5.add(myDataBase.getAllSeats().filter { it.Block == "BlockB" }.map { it.SeatRow }.distinct()[1].toString())
        arrayList5.add(myDataBase.getAllSeats().filter { it.Block == "BlockB" }.map { it.SeatRow }.distinct()[2].toString())

        //adds the seat rows to the dropdown menu
        val sRow2: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList5)
        sRow2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        //if block A is selected from the dropdown menu then the seat row associated with block A are displayed otherwise seat rows associated
        //with block B are displayed in the dropdown menu
        bookingBlock.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(bookingBlock.selectedItem == "A"){
                    bookingRow.setAdapter(sRow)
                }else {
                    bookingRow.setAdapter(sRow2)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        //displays the seat numbers in the dropdown menu depending on the seat row selected
        bookingRow.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(bookingRow.selectedItem == "A"){
                    bookingSeat.setAdapter(seatNumberA)
                }else if (bookingRow.selectedItem == "B") {
                    bookingSeat.setAdapter(seatNumberB)
                }else if (bookingRow.selectedItem == "C") {
                    bookingSeat.setAdapter(seatNumberC)
                }else if (bookingRow.selectedItem == "D") {
                    bookingSeat.setAdapter(seatNumberD)
                }else if (bookingRow.selectedItem == "E") {
                    bookingSeat.setAdapter(seatNumberE)
                }else if (bookingRow.selectedItem == "F") {
                    bookingSeat.setAdapter(seatNumberF)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        //gets the price for that match
        val price = myDataBase.getAllMatches().filter { it.DateAndTime == dat.text.toString()}.map { it.Price }[0]

        //books the ticket for the user
        book.setOnClickListener {
            val intent = Intent(this, BookingDetails::class.java)

            myDataBase.addBooking(Booking(-1, name.text.toString(), bookingBlock.selectedItem.toString(), dat.text.toString(), bookingSeat.selectedItem.toString().toInt(), bookingRow.selectedItem.toString(),price))

            //deletes the seat number that was chosen by the user
            myDataBase.deleteSeat(bookingSeat.selectedItem.toString())

            intent.putExtra("name", name.text.toString())
            intent.putExtra("d", dat.text.toString())
            intent.putExtra("homeTeam", home.text.toString())
            intent.putExtra("awayTeam", away.text.toString())
            startActivity(intent)
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}