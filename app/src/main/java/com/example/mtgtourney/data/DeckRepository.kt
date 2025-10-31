package com.example.mtgtourney.data

import android.content.Context
import android.util.Log
import com.example.mtgtourney.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DeckRepository {

    suspend fun getDecks(appContext: Context): List<Deck> =
        withContext(Dispatchers.IO) {
            val decks = try {
                val deckListFile =
                    appContext.openFileInput(DECK_LIST).bufferedReader().useLines { lines ->
                        lines.fold("") { start, end ->
                            "$start $end"
                        }
                    }
                    toList(deckListFile)
                } catch (e: Exception) {
                    null
                }
            if (decks.isNullOrEmpty()) {
                toList(appContext.resources.openRawResource(R.raw.initial_decks).bufferedReader()
                    .useLines { lines ->
                        lines.fold("") { start, end ->
                            "$start $end"
                        }
                    })
            } else {
                decks
            }

        }

    private fun toList(json: String): List<Deck> {
        val gson = Gson()
        return gson.fromJson(json, object : TypeToken<List<Deck>>() {}.type)
    }

}