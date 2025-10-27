package com.example.mtgtourney

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtgtourney.data.DeckRepository
import com.example.mtgtourney.data.Tournament
import com.example.mtgtourney.data.TournamentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(): ViewModel() {

    val tournamentRepository = TournamentRepository()
    val deckRepository = DeckRepository()
    val tournamentLiveData: MutableLiveData<Tournament> = MutableLiveData()

    fun initTournament(context: Context) {
        viewModelScope.launch {
            val tournament = withContext(Dispatchers.IO) {
                // This block runs on the IO dispatcher (off the main thread)
                // Perform network request or database query here
                tournamentRepository.getTournament(context, deckRepository.getDecks(context))
            }
            withContext(Dispatchers.Main) {
                tournamentLiveData.value = tournament
            }
        }
    }

    fun resetTournament(context: Context) {
        viewModelScope.launch {
            tournamentRepository.updateTournament(context, Tournament())
            initTournament(context)
        }
    }

    fun updateTournament(context: Context, tournament: Tournament) {
        viewModelScope.launch {
            tournamentRepository.updateTournament(context, tournament)
        }
    }
}