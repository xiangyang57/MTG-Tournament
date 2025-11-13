package com.example.mtgtourney

import com.example.mtgtourney.data.Deck
import com.example.mtgtourney.data.Match
import com.example.mtgtourney.data.Tournament
import kotlin.math.min

// Valid match will be created if deck size is not divisible by 2 the last deck will be cut
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
    return Tournament(mutableListOf(matches))
}

