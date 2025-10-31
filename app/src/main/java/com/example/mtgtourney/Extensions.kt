package com.example.mtgtourney

import com.example.mtgtourney.data.Deck
import com.example.mtgtourney.data.Match
import com.example.mtgtourney.data.Tournament
import kotlin.math.min

fun List<Deck>.createTournament(tournamentSize: Int = Int.MAX_VALUE): Tournament {
    val targetSize = min(size, tournamentSize)
    if (targetSize < 2) {
        return Tournament()
    }
    val shuffled = this.shuffled()
    val matches = mutableListOf<Match>()

    for (i in 1 until targetSize step 2) {
        matches.add(Match(shuffled[i-1], shuffled[i]))
    }
    if (targetSize % 2 == 1) {
        matches.add(Match(shuffled[shuffled.size-1], null, shuffled[shuffled.size-1]))
    }
    return Tournament(mutableListOf(matches))
}

