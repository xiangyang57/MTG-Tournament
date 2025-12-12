package com.example.mtgtourney.data.deck

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Deck(
    val commander:String,
    val colors:List<com.example.mtgtourney.data.Color>,
): Parcelable