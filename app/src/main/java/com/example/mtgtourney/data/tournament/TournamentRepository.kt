package com.example.mtgtourney.data.tournament

import android.content.Context
import com.example.mtgtourney.createTournament
import com.example.mtgtourney.data.Deck.Deck
import com.example.mtgtourney.data.TOURNAMENT
import com.google.gson.Gson
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class TournamentRepository @Inject constructor() {

    private var current: Tournament? = null

    suspend fun getTournament(appContext: Context, decks: List<Deck>): Tournament {
        if (current != null) {
            return current!!
        }
        return getTournamentFromFile(appContext, decks)
    }
    private suspend fun getTournamentFromFile(appContext: Context, decks: List<Deck>): Tournament =
        withContext(Dispatchers.IO) {
            val existingTournament = try {
                val tournamentFile =
                    appContext.openFileInput(TOURNAMENT).bufferedReader().useLines { lines ->
                        lines.fold("") { start, end ->
                            "$start $end"
                        }
                    }
                val gson = Gson()

                gson.fromJson(tournamentFile, Tournament::class.java)
            } catch (e: Exception) {
                null
            }
            if (existingTournament == null || existingTournament.brackets.isEmpty()) {
                val tournament = decks.createTournament()
                updateTournament(appContext, tournament)
                current = tournament
                tournament
            } else {
                current = existingTournament
                existingTournament
            }
        }

    /**
     * Recreate the tournament based on size
     */
    suspend fun updateTournament(appContext: Context, tournament: Tournament) {
        current = tournament
        withContext(Dispatchers.IO) {
            appContext.deleteFile(TOURNAMENT)
            appContext.openFileOutput(TOURNAMENT, Context.MODE_PRIVATE).use {
                it.write(Gson().toJson(tournament).toByteArray())
                it.flush()
            }
        }
    }
}
