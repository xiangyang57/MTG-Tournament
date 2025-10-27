package com.example.mtgtourney.data

import android.content.Context
import com.example.mtgtourney.createTournament
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.gson.Gson;

class TournamentRepository {

    suspend fun getTournament(appContext: Context, decks: List<Deck>): Tournament =
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
                tournament
            } else {
                existingTournament
            }
        }

    suspend fun updateTournament(appContext: Context, tournament: Tournament) {
        withContext(Dispatchers.IO) {
            appContext.deleteFile(TOURNAMENT)
            appContext.openFileOutput(TOURNAMENT, Context.MODE_PRIVATE).use {
                it.write(Gson().toJson(tournament).toByteArray())
                it.flush()
            }
        }
    }
}
