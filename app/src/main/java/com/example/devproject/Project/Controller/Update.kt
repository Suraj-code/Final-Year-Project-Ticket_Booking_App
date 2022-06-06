package com.example.devproject.Project.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.devproject.Project.Model.Database
import com.example.devproject.Project.Model.Matches
import com.example.devproject.R

class Update : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val matchIdDropdown = findViewById<Spinner>(R.id.matchIdSpinner)
        val hTeamDropdown = findViewById<Spinner>(R.id.homeTeamDropdown)
        val aTeamDropdown = findViewById<Spinner>(R.id.awayTeamDropdown)
        val homeTeamName = findViewById<TextView>(R.id.homeTeamS3)
        val awayTeamName = findViewById<TextView>(R.id.awayTeamText)
        val stadiumName = findViewById<TextView>(R.id.stadiumText)
        val updateDate = findViewById<TextView>(R.id.dateTextUpdate)
        val price = findViewById<TextView>(R.id.priceEditText2)
        val updateBtn = findViewById<Button>(R.id.updateButton)
        val updateBack = findViewById<Button>(R.id.updateBackBtn)

        updateBack.setOnClickListener {
            val intent = Intent(this, AdminActivity2::class.java)
            startActivity(intent)
            finish()
        }

        val myDataBase = Database(this)

        //gets all the matchIds and stores them in an array list
        val arrayList: ArrayList<String> = ArrayList()
        for(num in 0 until myDataBase.getAllMatches().size){
            arrayList.add(myDataBase.getAllMatches().map { it.MatchId }[num].toString())
        }

        //gets all images and stores them in array list
        val imagesList: ArrayList<ByteArray> = ArrayList()
        for(num in 0..19){
            val image = myDataBase.getAllTeams().map { it.Image }[num]
//            val bmp: Bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
            imagesList.add(image)
        }

        //images and match Ids are added to the dropdown menu (spinner)
        val hImages: ArrayAdapter<ByteArray> =
            ArrayAdapter<ByteArray>(this, android.R.layout.simple_spinner_item, imagesList)
        hImages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hTeamDropdown.setAdapter(hImages)

        val aImages: ArrayAdapter<ByteArray> =
            ArrayAdapter<ByteArray>(this, android.R.layout.simple_spinner_item, imagesList)
        aImages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        aTeamDropdown.setAdapter(aImages)

        val matchIds: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList)
        matchIds.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        matchIdDropdown.setAdapter(matchIds)

        var index = 0

        //brings the match details associated with the match Id selected from the dropdown menu
        matchIdDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                for(num in 1 until myDataBase.getAllMatches().size) {
                    if (matchIdDropdown.selectedItem == num.toString()) {
                        homeTeamName.text = myDataBase.getAllMatches().map { it.HomeTeam }[index].toString()
                        awayTeamName.text = myDataBase.getAllMatches().map { it.AwayTeam }[index].toString()
                        stadiumName.text = myDataBase.getAllMatches().map { it.Stadium }[index].toString()
                        updateDate.text = myDataBase.getAllMatches().map { it.DateAndTime }[index].toString()
                        price.text = myDataBase.getAllMatches().map { it.Price }[index].toString()
                        index++
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        //updates the match details
        updateBtn.setOnClickListener {

            if(myDataBase.updateMatchDetails(
                    Matches(matchIdDropdown.selectedItem.toString().toInt(), homeTeamName.text.toString(), awayTeamName.text.toString(),
                updateDate.text.toString(), hTeamDropdown.selectedItem as ByteArray, aTeamDropdown.selectedItem as ByteArray, stadiumName.text.toString(),
                price.text.toString().toInt())
                )){
                Toast.makeText(this, "Update Successful!", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Update not Successful!", Toast.LENGTH_SHORT).show()
            }

        }

    }
}