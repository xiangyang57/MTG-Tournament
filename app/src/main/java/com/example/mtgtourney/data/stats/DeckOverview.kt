package com.example.mtgtourney.data.stats

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mtgtourney.data.Deck

@Entity(tableName = "deckOverview")
data class DeckOverview(
    val deck: Deck,
    var tournamentWin: Int = 0,
    var participation: Int = 0,
    var overallWin: Int = 0,
    var overallLoss: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0)
