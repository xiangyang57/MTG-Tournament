package com.example.mtgtourney.data

data class Tournament(
    // Each list of matches represents a tier of the competition with all competitors in the
    // beginning and finalists at end of list
    val brackets: MutableList<MutableList<Match>> = mutableListOf()
)