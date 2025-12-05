package com.example.mtgtourney.data.tournament

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class TournamentRepository @Inject constructor(
    private val tournamentDao: TournamentDao
) {

    private var current: Tournament? = null

    suspend fun getTournament(): Tournament? {
        if (current != null) {
            return current!!
        }
        return withContext(Dispatchers.IO) {
            current = tournamentDao.getLatestTournament()
            current
        }
    }

    /**
     * Recreate the tournament based on size
     */
    suspend fun updateTournament(tournament: Tournament) {
        current = tournament
        withContext(Dispatchers.IO) {
            tournamentDao.upsertTournament(tournament)
        }
    }
}
