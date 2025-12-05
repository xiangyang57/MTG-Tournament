package com.example.mtgtourney.data.tournament

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mtgtourney.data.tournament.Match

@Entity(tableName = "tournament")
data class Tournament(
    // Each list of matches represents a tier of the competition with all competitors in the
    // beginning and finalists at end of list
    val brackets: MutableList<MutableList<Match>> = mutableListOf(),
    val timeStamp: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)