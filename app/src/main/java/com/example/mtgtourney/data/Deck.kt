package com.example.mtgtourney.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Deck(
    val commander:String,
    val colors:List<Color>,
    val win:Int = 0,
    val lose:Int = 0,
): Parcelable