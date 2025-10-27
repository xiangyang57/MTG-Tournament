package com.example.mtgtourney.data

data class Match(
    val playerA: Deck,
    val playerB: Deck? = null,
    var winner: Deck? = null
)