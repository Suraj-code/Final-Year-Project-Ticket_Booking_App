package com.example.devproject.Project.Controller

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.devproject.Project.Model.Database
import com.example.devproject.Project.Model.Matches
import com.example.devproject.R
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class AdminActivity2 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin2)

        val backBtn = findViewById<Button>(R.id.adminBackButton)
        val date = findViewById<ImageButton>(R.id.dateBtn)
        val dateTV = findViewById<TextView>(R.id.dateTv)
        val addBtn = findViewById<Button>(R.id.addMatchButton)
        val delete = findViewById<Button>(R.id.deleteMatchButton)
        val update = findViewById<Button>(R.id.updateBtn)

        //Re-directed to the update page
        update.setOnClickListener {
            val intent = Intent(this, Update::class.java)
            startActivity(intent)
            finish()
        }

        //Re-directed to the delete page
        delete.setOnClickListener {

            val intent = Intent(this, deleteMatches::class.java)
            startActivity(intent)
            finish()

        }

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month  = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        //allows admin to pick the date
        date.setOnClickListener {
            val dPicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, nYear, nMonth, nDay ->
                dateTV.text = (""+ nDay + "/"+ (nMonth+1) + "/" + nYear)
            }, year, month, day)

            dPicker.show()
        }


        //Re-directed to the main page
        backBtn.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        val hTeamDropdown = findViewById<Spinner>(R.id.homeTeamS)
        val aTeamDropdown = findViewById<Spinner>(R.id.awayTeamS)
        val stadiumDropdown = findViewById<Spinner>(R.id.stadiumNameS)
        val hTeamImgDropdown = findViewById<Spinner>(R.id.HomeImgS)
        val aTeamImgDropdown = findViewById<Spinner>(R.id.AwayImgS)
        val price = findViewById<EditText>(R.id.priceEditText)


        val myDataBase = Database(this)

        //gets all teams and stores them in an array list
        val arrayList: ArrayList<String> = ArrayList()
        for(num in 0..19){
            arrayList.add(myDataBase.getAllTeams().map { it.Team }[num].toString())
        }

        //gets all stadium names and stores them in an array list
        val arrayList2: ArrayList<String> = ArrayList()
        for(num in 0..19){
            arrayList2.add(myDataBase.getAllTeams().map { it.Stadium }[num].toString())
        }

        //gets all images and stores them in an array list
        val imagesList: ArrayList<ByteArray> = ArrayList()
        for(num in 0..19){
            val image = myDataBase.getAllTeams().map { it.Image }[num]
//            val bmp: Bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
            imagesList.add(image)
        }

        //all teams, stadium name and images are added to the dropdown menu (spinner)
        val homeTeam: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList)
        homeTeam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hTeamDropdown.setAdapter(homeTeam)

        val awayTeam: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList)
        awayTeam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        aTeamDropdown.setAdapter(awayTeam)

        val stadium: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList2)
        stadium.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stadiumDropdown.setAdapter(stadium)

        val hImages: ArrayAdapter<ByteArray> =
            ArrayAdapter<ByteArray>(this, android.R.layout.simple_spinner_item, imagesList)
        hImages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hTeamImgDropdown.setAdapter(hImages)

        val aImages: ArrayAdapter<ByteArray> =
            ArrayAdapter<ByteArray>(this, android.R.layout.simple_spinner_item, imagesList)
        aImages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        aTeamImgDropdown.setAdapter(aImages)


        //adds match to the database
        addBtn.setOnClickListener {
            val hTeam = hTeamDropdown.selectedItem.toString()

            val aTeam = aTeamDropdown.selectedItem.toString()

            val dateAndTime = dateTV.text.toString()
            val std = stadiumDropdown.selectedItem.toString()

            val p = price.text.toString().toInt()

            val imagesList2: ArrayList<ByteArray> = ArrayList()
            for(num in 0..19){
                val image = myDataBase.getAllTeams().map { it.Image }[num]
                val bmp: Bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
                val stream = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.PNG,0,stream)
                val byteImage: ByteArray = stream.toByteArray()
                imagesList2.add(image)

            }
            val hTeamImg = hTeamImgDropdown.selectedItem
            val aTeamImg = aTeamImgDropdown.selectedItem

            if(myDataBase.addMatch(Matches(-1,hTeam, aTeam, dateAndTime,
                hTeamImg as ByteArray, aTeamImg as ByteArray,std, p))){
                    Toast.makeText(this, "Match added!", Toast.LENGTH_SHORT).show()
                }else {
                Toast.makeText(this, "Match not added!", Toast.LENGTH_SHORT).show()
            }

        }


    }

}