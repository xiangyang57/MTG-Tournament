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
class StatsRepository @Inject constructor(
    private val deckOverviewDao: DeckOverviewDao
) {

    private val overview: MutableList<DeckOverview> = mutableListOf()
    private val deckStatsMap = hashMapOf<String, DeckOverview>()

    suspend fun getStats(): List<DeckOverview> =
        if (overview.isEmpty()) getStatsFromFile() else overview

    private suspend fun getStatsFromFile(): List<DeckOverview> =
        withContext(Dispatchers.IO) {
            overview.clear()
            overview.addAll(deckOverviewDao.getAllDeckOverviews())
            refreshMapping()
            overview
        }

    private suspend fun updateStat(deckOverview: DeckOverview) {
        deckOverviewDao.updateDeckOverView(deckOverview)
    }

    private suspend fun addStat(deckOverview: DeckOverview) {
        deckOverviewDao.addDeckOverView(deckOverview)
    }



    suspend fun logMatchResult(context: Context, winner: Deck, loser:Deck, isFinals: Boolean) {
        if (overview.isEmpty()) {
            getStats()
        }
        if (!deckStatsMap.contains(winner.commander)) {
            val deckOverview = DeckOverview(winner)
            overview.add(deckOverview)
            addStat(deckOverview)
            deckStatsMap.put(winner.commander, deckOverview)
        }
        deckStatsMap.get(winner.commander)?.let {
            it.overallWin++
            if (isFinals) {
                it.tournamentWin++
            }
            updateStat(it)
        }

        if (!deckStatsMap.contains(loser.commander)) {
            val deckOverview = DeckOverview(loser)
            overview.add(deckOverview)
            deckStatsMap.put(loser.commander, deckOverview)
            addStat(deckOverview)
        }
        deckStatsMap.get(loser.commander)?.let {
            it.overallLoss++
            updateStat(it)
        }
    }

    private fun refreshMapping() {
        deckStatsMap.clear()
        for (stat in overview) {
            deckStatsMap.put(stat.deck.commander, stat)
        }
    }
}