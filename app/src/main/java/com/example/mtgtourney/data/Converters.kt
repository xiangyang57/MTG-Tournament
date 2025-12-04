package com.example.mtgtourney.data

import androidx.room.TypeConverter
import com.example.mtgtourney.data.Deck.Deck
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromDeck(deck: Deck): String {
        return gson.toJson(deck)
    }

    @TypeConverter
    fun toDeck(value: String): Deck {
        val type = object : TypeToken<Deck>() {}.type
        return gson.fromJson(value, type)
    }
}