package com.example.mtgtourney.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.contains

@ActivityRetainedScoped
class StatsRepository @Inject constructor() {

    private val overview: MutableList<DeckOverview> = mutableListOf()
    private val deckStatsMap = hashMapOf<String, DeckOverview>()

    suspend fun getStats(appContext: Context): List<DeckOverview> =
        if (overview.isEmpty()) getStatsFromFile(appContext) else overview

    private suspend fun getStatsFromFile(appContext: Context): List<DeckOverview> =
        withContext(Dispatchers.IO) {
            try {
                val statsFile =
                    appContext.openFileInput(STATS).bufferedReader().useLines { lines ->
                        lines.fold("") { start, end ->
                            "$start $end"
                        }
                    }
                val gson = Gson()
                val stats: List<DeckOverview> =
                    gson.fromJson(statsFile, object : TypeToken<List<DeckOverview>>() {}.type)
                overview.clear()
                overview.addAll(stats)
                refreshMapping()
                stats
            } catch (e: Exception) {
                listOf()
            }
        }

    /**
     * Recreate the tournament based on size
     */
    private suspend fun updateStats(appContext: Context, stats: List<DeckOverview>) {
        withContext(Dispatchers.IO) {
            appContext.deleteFile(STATS)
            appContext.openFileOutput(STATS, Context.MODE_PRIVATE).use {
                it.write(Gson().toJson(stats).toByteArray())
                it.flush()
            }
        }
    }

    suspend fun logMatchResult(context: Context, winner: Deck, loser:Deck, isFinals: Boolean) {
        if (overview.isEmpty()) {
            getStats(context)
        }
        if (!deckStatsMap.contains(winner.commander)) {
            val deckOverview = DeckOverview(winner)
            overview.add(deckOverview)
            deckStatsMap.put(winner.commander, deckOverview)
        }
        deckStatsMap.get(winner.commander)?.let {
            it.overallWin++
            if (isFinals) {
                it.tournamentWin++
            }
        }

        if (!deckStatsMap.contains(loser.commander)) {
            val deckOverview = DeckOverview(loser)
            overview.add(deckOverview)
            deckStatsMap.put(loser.commander, deckOverview)
        }
        deckStatsMap.get(loser.commander)?.let {
            it.overallLoss++
        }
        updateStats(context, overview)
    }

    private fun refreshMapping() {
        deckStatsMap.clear()
        for (stat in overview) {
            deckStatsMap.put(stat.deck.commander, stat)
        }
    }
}