package com.example.mtgtourney.data.tournament

import android.os.Parcelable
import com.example.mtgtourney.data.Deck.Deck
import kotlinx.parcelize.Parcelize

@Parcelize
data class Match(
    val playerA: Deck,
    val playerB: Deck,
    var winner: Deck? = null
) : Parcelable