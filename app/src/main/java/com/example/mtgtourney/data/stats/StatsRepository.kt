package com.example.mtgtourney.data.stats

import com.example.mtgtourney.data.deck.Deck
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class StatsRepository @Inject constructor(
    private val deckOverviewDao: DeckOverviewDao
) {

    private val overview: MutableList<DeckOverview> = mutableListOf()
    private val deckStatsMap = hashMapOf<String, DeckOverview>()
    private val mutex = Mutex()
    suspend fun getStats(): List<DeckOverview> =
        if (overview.isEmpty()) getStatsFromFile() else overview


    private suspend fun getStatsFromFile(): List<DeckOverview> =
        withContext(Dispatchers.IO) {
            mutex.withLock {
                overview.clear()
                overview.addAll(deckOverviewDao.getAllDeckOverviews())
                refreshMapping()
                overview
            }
        }

    suspend fun logMatchResult(winner: Deck, loser: Deck, isFinals: Boolean) {
        if (overview.isEmpty()) {
            getStats()
        }
        if (!deckStatsMap.contains(winner.commander)) {
            val deckOverview = DeckOverview(winner)
            overview.add(deckOverview)
            deckStatsMap.put(winner.commander, deckOverview)
        }
        deckStatsMap[winner.commander]?.let {
            it.overallWin++
            if (isFinals) {
                it.tournamentWin++
            }
            deckOverviewDao.upsertDeckOverView(it)
        }

        if (!deckStatsMap.contains(loser.commander)) {
            val deckOverview = DeckOverview(loser)
            overview.add(deckOverview)
            deckStatsMap.put(loser.commander, deckOverview)
        }
        deckStatsMap[loser.commander]?.let {
            it.overallLoss++
            deckOverviewDao.upsertDeckOverView(it)
        }
    }

    private fun refreshMapping() {
        deckStatsMap.clear()
        for (stat in overview) {
            deckStatsMap.put(stat.deck.commander, stat)
        }
    }
}