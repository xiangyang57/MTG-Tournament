package com.example.mtgtourney.data.deck

import android.content.Context
import com.example.mtgtourney.R
import com.google.gson.Gson
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class DeckRepository @Inject constructor(){

    suspend fun getDecks(appContext: Context): List<Deck> =
        withContext(Dispatchers.IO) {
            val decks = try {
                val deckListFile =
                    appContext.openFileInput(_root_ide_package_.com.example.mtgtourney.data.DECK_LIST).bufferedReader().useLines { lines ->
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
        return gson.fromJson(json, object : com.google.gson.reflect.TypeToken<List<Deck>>() {}.type)
    }
}