package com.example.mtgtourney

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtgtourney.data.Deck
import com.example.mtgtourney.data.DeckOverview
import com.example.mtgtourney.data.DeckRepository
import com.example.mtgtourney.data.StatsRepository
import com.example.mtgtourney.data.Tournament
import com.example.mtgtourney.data.TournamentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(): ViewModel() {

    val tournamentRepository = TournamentRepository()
    val deckRepository = DeckRepository()
    val statsRepository = StatsRepository()
    val tournamentLiveData: MutableLiveData<Tournament> = MutableLiveData()
    val overviewLiveData: MutableLiveData<MutableList<DeckOverview>> = MutableLiveData()
    private val deckStatsMap = hashMapOf<String, DeckOverview>()

    fun initTournament(context: Context) {
        viewModelScope.launch {
            val tournament =
                // This block runs on the IO dispatcher (off the main thread)
                // Perform network request or database query here
                tournamentRepository.getTournament(context, deckRepository.getDecks(context))
            withContext(Dispatchers.Main) {
                tournamentLiveData.value = tournament
            }
        }
    }

    fun resetTournament(context: Context, tournamentSize: Int) {
        viewModelScope.launch {
            val decks = deckRepository.getDecks(context)
            tournamentRepository.updateTournament(
                context,
                decks.createTournament(tournamentSize))
            initTournament(context)
        }
    }

    fun updateTournament(context: Context, tournament: Tournament) {
        viewModelScope.launch {
            tournamentRepository.updateTournament(context, tournament)
        }
    }

    fun updateStats(context: Context, winner: Deck, loser: Deck) {
        viewModelScope.launch {
            if (overviewLiveData.value == null) {
                statsRepository.getStats(context).run {
                    val stats = this.toMutableList()
                    overviewLiveData.value = stats
                    refreshMapping(stats)
                }
            }
            if (!deckStatsMap.contains(winner.commander)) {
                val deckOverview = DeckOverview(winner)
                overviewLiveData.value?.apply {
                    add(deckOverview)
                }
            }
            deckStatsMap.get(winner.commander)?.let {
                it.overallWin++
            }

            if (!deckStatsMap.contains(loser.commander)) {
                val deckOverview = DeckOverview(winner)
                overviewLiveData.value?.apply {
                    add(deckOverview)
                }
            }
            deckStatsMap.get(winner.commander)?.let {
                it.overallLoss++
            }
            statsRepository.updateStats(context, overviewLiveData.value!!)

        }
    }

    fun refreshMapping(stats: List<DeckOverview>) {
        deckStatsMap.clear()
        for (stat in stats) {
            deckStatsMap.put(stat.deck.commander, stat)
        }
    }


}