package com.example.mtgtourney.data

import androidx.room.TypeConverter
import com.example.mtgtourney.data.deck.Deck
import com.example.mtgtourney.data.tournament.Match
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

    @TypeConverter
    fun fromMatchList(matchList: MutableList<MutableList<Match>>): String {
        return gson.toJson(matchList)
    }

    @TypeConverter
    fun toMatchList(value: String): MutableList<MutableList<Match>> {
        val type = object : TypeToken<MutableList<MutableList<Match>>>() {}.type
        return gson.fromJson(value, type)
    }
}