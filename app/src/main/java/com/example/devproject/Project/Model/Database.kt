package com.example.devproject.Project.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

/* Database Config*/
private val DataBaseName = "FTSdatabase.db"
private val ver : Int = 1


class Database(context: Context) : SQLiteOpenHelper(context, DataBaseName,null , ver) {

    /* User Table */
    public val UserTableName = "User"
    public val UserColumnId = "userId"
    public val UserNameColumn = "Name"
    public val ColumnPassword = "Password"
    public val ColumnEmailId = "EmailId"
    public val ColumnMobileNo = "MobileNo"
    public val ColumnGender = "Gender"

    /*************************/

    /* Team Table */
    public val TeamTableName = "Teams"
    public val NameColumn = "Stadium"
    public val LocationCoulmn = "Location"
    public val TeamColumn = "Team"
    public val ImageColumn = "Image"

    /*************************/

    /*Seat Table*/
    public val SeatTableName = "Seat"
    public val SeatNoColumn = "SeatNo"
    public val SeatRowColumn = "seatRow"
    public val blockSColumn = "Block"


    /*************************/

    /*Block Table*/
    public val BlockTableName = "Block"
    public val BlockIdColumn = "BlockId"
    public val BlockNameColumn = "Block"

    /*************************/

    /*Booking Table*/
    public val BookingTableName = "BookingTable"
    public val BookingIdColumn = "BookingId"
    public val NameFColumn = "Name"
    public val BlockFColumn = "Block"
    public val MatchDateColumn = "MatchDate"
    public val seatFColumn = "Seat"
    public val bSeatRowColumn = "SeatRow"
    public val bPriceColumn = "Price"

    /*************************/

    /*Match Details Table*/
    public val MatchTableName = "MatchDetails"
    public val MatchIdColumn = "MatchId"
    public val HTColumn = "HomeTeam"
    public val ATColumn = "AwayTeam"
    public val DATColumn = "DateAndTime"
    public val HTIColumn = "HomeTeamImg"
    public val ATIColumn = "AwayTeamImg"
    public val MStadiumColumn = "Stadium"
    public val PriceColumn = "Price"




    /*************************/

    // This is called the first time a database is accessed
    // Create a new database
    override fun onCreate(db: SQLiteDatabase?) {


        try {
            var sqlCreateStatement: String =
                "CREATE TABLE " + UserTableName + " ( " + UserColumnId +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " + UserNameColumn + " TEXT NOT NULL, " + ColumnPassword + " TEXT NOT NULL, " +
                        ColumnEmailId + " TEXT NOT NULL, " + ColumnMobileNo + " INTEGER NOT NULL, " + ColumnGender + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " + TeamTableName + " ( " + NameColumn +
                    " INTEGER PRIMARY KEY, " + LocationCoulmn + " TEXT NOT NULL, " + TeamColumn + " TEXT NOT NULL, " + ImageColumn + " TEXT ) "

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement =
                "CREATE TABLE " + SeatTableName + " ( " + SeatNoColumn +
                    " INTEGER PRIMARY KEY, " + SeatRowColumn + " TEXT NOT NULL, " + blockSColumn + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " + BlockTableName + " ( " + BlockIdColumn +
                    " INTEGER PRIMARY KEY, " + BlockNameColumn + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement =
            "CREATE TABLE " + BookingTableName + " ( " + BookingIdColumn +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + SeatRowColumn + " TEXT NOT NULL, " + NameFColumn + " TEXT NOT NULL, " +
                    BlockFColumn + " TEXT NOT NULL, " + MatchDateColumn + " INTEGER NOT NULL, " + seatFColumn + " INTEGER NOT NULL, " +
            bSeatRowColumn + " TEXT NOT NULL " + bPriceColumn + " INTEGER NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement =
                "CREATE TABLE " + MatchTableName + " ( " + MatchIdColumn +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " + HTColumn + " TEXT NOT NULL, " + ATColumn + " TEXT NOT NULL, " +
                        DATColumn + " NUMERIC NOT NULL, " + HTIColumn + " TEXT, " + ATIColumn + " TEXT, " + MStadiumColumn + " TEXT NOT NULL, " +
                        PriceColumn + "INTEGER NOT NULL )"

            db?.execSQL(sqlCreateStatement)




        }
        catch(e : SQLException) { }

    }

    // This is called if the database ver. is changed
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    //updates the database
    fun updateMatchDetails(match: Matches): Boolean {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(HTColumn, match.HomeTeam)
        cv.put(ATColumn, match.AwayTeam)
        cv.put(HTIColumn, match.HomeTeamImg)
        cv.put(ATIColumn, match.AwayTeamImg)
        cv.put(MStadiumColumn, match.Stadium)
        cv.put(DATColumn, match.DateAndTime)
        cv.put(PriceColumn, match.Price)

        val result = db.update(MatchTableName, cv, "$MatchIdColumn=?", arrayOf(match.MatchId.toString())) == 1
        db.close()
        return result
    }

    //Gets all the block from the database
    fun getAllBlocks(): ArrayList<Block> {

        val blockList = ArrayList<Block>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $BlockTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val blockId: String = cursor.getString(0)
                val blockName: String = cursor.getString(1)
                val b = Block(blockId, blockName)
                blockList.add(b)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return blockList
    }

    //gets all the seats from the database
    fun getAllSeats(): ArrayList<Seat> {

        val seatList = ArrayList<Seat>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $SeatTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val seatNo: Int = cursor.getInt(0)
                val seatRow: String = cursor.getString(1)
                val block: String = cursor.getString(2)
                val s = Seat(seatNo, seatRow, block)
                seatList.add(s)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return seatList
    }

    //Adds a match to the database
    fun addMatch(match: Matches): Boolean {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

//        cv.put(MatchIdColumn, match.ID)
        cv.put(HTColumn, match.HomeTeam)
        cv.put(ATColumn, match.AwayTeam)
        cv.put(DATColumn, match.DateAndTime)
        cv.put(HTIColumn, match.HomeTeamImg)
        cv.put(ATIColumn, match.AwayTeamImg)
        cv.put(MStadiumColumn, match.Stadium)
        cv.put(PriceColumn, match.Price)


        val success = db.insert(MatchTableName, null, cv)
        db.close()
        return success != -1L


    }

    //deletes a match
    fun deleteMatch(match: String): Boolean {

        val db: SQLiteDatabase = this.writableDatabase

        val result = db.delete(MatchTableName, "$MatchIdColumn=?", arrayOf(match)) == 1

        db.close()
        return result

    }

    //deletes a seat
    fun deleteSeat(Num: String): Boolean {

        val db: SQLiteDatabase = this.writableDatabase

        val result = db.delete(SeatTableName, "$SeatNoColumn=?", arrayOf(Num)) == 1

        db.close()
        return result

    }

    //adds user to database
    fun addUser(user: User) : Int {

        val isUserNameAlreadyExists = checkUserName(user) // check if the username is already exist in the database
        if(isUserNameAlreadyExists < 0)
            return isUserNameAlreadyExists

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(UserNameColumn, user.name)
        cv.put(ColumnPassword,user.password)
        cv.put(ColumnEmailId, user.EmailID)
        cv.put(ColumnMobileNo, user.MobileNo)
        cv.put(ColumnGender, user.gender)

        val success  =  db.insert(UserTableName, null, cv)

        db.close()
        if (success.toInt() == -1) return success.toInt() //Error, adding new user
        else return 1

    }

    private fun checkUserName(user: User): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = user.name.lowercase()

        val sqlStatement = "SELECT * FROM $UserTableName WHERE $UserNameColumn = ?"
        val param = arrayOf(userName)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)

        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return -3 // error the user name is already exist
        }

        cursor.close()
        db.close()
        return 0 //User not found

    }

    //gets the user details
    fun getUser(user: User) : Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = user.EmailID
        val userPassword = user.password

        val sqlStatement = "SELECT * FROM $UserTableName WHERE $ColumnEmailId = ? AND $ColumnPassword = ?"
        val param = arrayOf(userName,userPassword)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }

        cursor.close()
        db.close()
        return -1 //User not found

    }

    //Gets all the teams from the database
    fun getAllTeams(): ArrayList<Teams> {

        val teamList = ArrayList<Teams>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $TeamTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val Team: String = cursor.getString(0)
                val Location: String = cursor.getString(1)
                val Stadium: String = cursor.getString(2)
                val Image: ByteArray = cursor.getBlob(3)
                val t = Teams(Team, Location, Stadium, Image)
                teamList.add(t)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return teamList
    }

    //Gets all the matches from the database
    fun getAllMatches(): ArrayList<Matches> {

        val matchList = ArrayList<Matches>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $MatchTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val ID: Int = cursor.getInt(0)
                val HomeTeam: String = cursor.getString(1)
                val AwayTeam: String = cursor.getString(2)
                val DateAndTime: String = cursor.getString(3)
                val HomeTeamImg: ByteArray = cursor.getBlob(4)
                val AwayTeamImg: ByteArray = cursor.getBlob(5)
                val Stadium: String = cursor.getString(6)
                val Price: Int = cursor.getInt(7)
                val m = Matches(ID,HomeTeam, AwayTeam, DateAndTime, HomeTeamImg, AwayTeamImg, Stadium, Price)
                matchList.add(m)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return matchList
    }

    //Gets all the booking details from the database
    fun getAllBookingDetails(): ArrayList<Booking> {

        val bookingDet = ArrayList<Booking>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $BookingTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val BookingId: Int = cursor.getInt(0)
                val Name: String = cursor.getString(1)
                val Block: String = cursor.getString(2)
                val MatchDate: String = cursor.getString(3)
                val Seat: Int = cursor.getInt(4)
                val SeatRow: String = cursor.getString(5)
                val Price: Int = cursor.getInt(6)
                val b = Booking(BookingId, Name, Block, MatchDate, Seat, SeatRow, Price )
                bookingDet.add(b)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return bookingDet
    }

    //Adds booking to the database
    fun addBooking(book: Booking): Boolean {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

//        cv.put(BookingIdColumn, book.BookingId)
        cv.put(NameFColumn, book.Name)
        cv.put(BlockFColumn, book.Block)
        cv.put(MatchDateColumn, book.MatchDate)
        cv.put(seatFColumn, book.Seat)
        cv.put(bSeatRowColumn, book.SeatRow)
        cv.put(bPriceColumn, book.Price)


        val success = db.insert(BookingTableName, null, cv)
        db.close()
        return success != -1L


    }

}

