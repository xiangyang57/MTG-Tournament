package com.example.mtgtourney.data

data class DeckOverview(
    val deck: Deck,
    var tournamentWin: Int = 0,
    var participation: Int = 0,
    var overallWin: Int = 0,
    var overallLoss: Int = 0
)