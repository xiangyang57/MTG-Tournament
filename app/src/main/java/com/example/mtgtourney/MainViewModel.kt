package com.example.mtgtourney

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtgtourney.data.Deck
import com.example.mtgtourney.data.DeckRepository
import com.example.mtgtourney.data.stats.StatsRepository
import com.example.mtgtourney.data.Tournament
import com.example.mtgtourney.data.TournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class MainViewModel@Inject constructor(
    private val tournamentRepository: TournamentRepository,
    private val deckRepository: DeckRepository,
    private val statsRepository: StatsRepository
) : ViewModel() {

    val tournamentLiveData: MutableLiveData<Tournament> = MutableLiveData()

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

    fun updateStats(winner: Deck, loser: Deck, isFinals: Boolean = false) {
        viewModelScope.launch {
            statsRepository.logMatchResult(winner, loser, isFinals)
        }
    }
}