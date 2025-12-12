package com.example.mtgtourney.ui.match

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtgtourney.data.deck.Deck
import com.example.mtgtourney.data.deck.DeckRepository
import com.example.mtgtourney.data.tournament.Match
import com.example.mtgtourney.data.tournament.Tournament
import com.example.mtgtourney.data.tournament.TournamentRepository
import com.example.mtgtourney.data.stats.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class CurrentMatchViewModel@Inject constructor(
    private val tournamentRepository: TournamentRepository,
    private val deckRepository: DeckRepository,
    private val statsRepository: StatsRepository
) : ViewModel() {
    private val winCount = 2

    private val _tournament = MutableStateFlow<Tournament?>(null)
    private val _match = MutableStateFlow<Match?>(null)
    val match = _match.asStateFlow()
    private val _player1VictoryCount = MutableStateFlow(0)
    val player1VictoryCount = _player1VictoryCount.asStateFlow()
    private val _player2VictoryCount = MutableStateFlow(0)
    val player2VictoryCount = _player2VictoryCount.asStateFlow()
    private val _selectedPlayer = MutableStateFlow<Deck?>(null)
    val selectedPlayer = _selectedPlayer.asStateFlow()

    fun loadNextMatch(context: Context, forceRefresh: Boolean = false) {
        if (_tournament.value != null && !forceRefresh) {
            getNextMatch(_tournament.value!!)
        } else {
            viewModelScope.launch {
                val tournament =
                // This block runs on the IO dispatcher (off the main thread)
                    // Perform network request or database query here
                    tournamentRepository.getTournament()
                withContext(Dispatchers.Main) {
                    _tournament.value = tournament
                    tournament?.let { getNextMatch(it) }
                }
            }
        }
    }

    fun selectPlayer(deck: Deck) {
        _selectedPlayer.value = deck
    }

    fun updateTournament(context: Context, match: Match) {
        _tournament.value?.let { tournament ->
            val currentBracket = tournament.brackets[tournament.brackets.lastIndex]
            if (currentBracket.size > 1 && match == currentBracket[currentBracket.lastIndex]) {
                val nextBracket = mutableListOf<Match>()
                for (i in 1 until currentBracket.size step 2) {
                    nextBracket.add(Match(currentBracket[i - 1].winner!!, currentBracket[i].winner!!))
                }
                tournament.brackets.add(nextBracket)
            }
            updateStats(match.winner!!,
                if (match.winner!! == match.playerA) match.playerB else match.playerA,
                currentBracket.size == 1)
            viewModelScope.launch {
                tournamentRepository.updateTournament(tournament)
            }
        }
    }

    fun confirmVictory() {
        _selectedPlayer.value?.let { winner ->
            if (_match.value?.playerA == winner) {
                _player1VictoryCount.value++
                if (_player1VictoryCount.value == winCount) {
                    _match.value?.winner = winner
                }
            } else if (_match.value?.playerB == winner) {
                _player2VictoryCount.value++
                if (_player2VictoryCount.value == winCount) {
                    _match.value?.winner = winner
                }
            }
        }
        _selectedPlayer.value = null
    }

    private fun getNextMatch(tournament: Tournament) {
        _player2VictoryCount.value = 0
        _player1VictoryCount.value = 0
        _selectedPlayer.value = null
        _match.value = null
        for (i in tournament.brackets.indices) {
            for (j in tournament.brackets[i].indices) {
                if (tournament.brackets[i][j].winner == null) {
                    _match.value = tournament.brackets[i][j]
                    break
                }
            }
        }
    }

    private fun updateStats(winner: Deck, loser: Deck, isFinals: Boolean = false) {
        viewModelScope.launch {
            statsRepository.logMatchResult(winner, loser, isFinals)
        }
    }

}