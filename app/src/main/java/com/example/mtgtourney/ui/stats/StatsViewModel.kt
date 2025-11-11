package com.example.mtgtourney.ui.stats

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtgtourney.data.DeckOverview
import com.example.mtgtourney.data.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class StatsViewModel @Inject constructor(
    val statsRepository: StatsRepository
) : ViewModel() {

    val stats = MutableLiveData<List<DeckOverview>>()

    fun getStats(context: Context) {
        viewModelScope.launch {
            val deck = statsRepository.getStats(context).toList().sortedWith(
                compareByDescending<DeckOverview> { it.tournamentWin }
                    .thenByDescending { it.overallWin - it.overallLoss}
                    .thenByDescending { it.overallWin + it.overallLoss })
            withContext(Dispatchers.Main) {
                stats.value = deck
            }
        }
    }
}