package com.example.devproject.Project.Model

data class Matches (val MatchId: Int, val HomeTeam: String, val AwayTeam: String, val DateAndTime: String, val HomeTeamImg: ByteArray, val AwayTeamImg: ByteArray, val Stadium: String, val Price: Int) {
}