package com.example.mtgtourney.data

data class Deck(
    val commander:String,
    val colors:List<Color>,
    val win:Int = 0,
    val lose:Int = 0,
)