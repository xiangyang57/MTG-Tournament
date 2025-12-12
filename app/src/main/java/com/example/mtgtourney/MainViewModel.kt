package com.example.mtgtourney

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtgtourney.data.deck.DeckRepository
import com.example.mtgtourney.data.tournament.Tournament
import com.example.mtgtourney.data.tournament.TournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class MainViewModel@Inject constructor(
    private val tournamentRepository: TournamentRepository,
    private val deckRepository: DeckRepository,
) : ViewModel() {

    val tournamentLiveData: MutableLiveData<Tournament?> = MutableLiveData()

    fun initTournament() {
        viewModelScope.launch {
            val tournament =
                // This block runs on the IO dispatcher (off the main thread)
                // Perform network request or database query here
                tournamentRepository.getTournament()
            withContext(Dispatchers.Main) {
                tournamentLiveData.value = tournament
            }
        }
    }

    fun resetTournament(context: Context, tournamentSize: Int) {
        viewModelScope.launch {
            val decks = deckRepository.getDecks(context)
            tournamentRepository.updateTournament(
                decks.createTournament(tournamentSize))
            initTournament()
        }
    }
}