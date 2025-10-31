package com.example.mtgtourney.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Match(
    val playerA: Deck,
    val playerB: Deck? = null,
    var winner: Deck? = null
) : Parcelable