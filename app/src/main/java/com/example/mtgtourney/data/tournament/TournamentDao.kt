package com.example.mtgtourney.data.tournament

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TournamentDao {

    @Query("SELECT * FROM tournament ORDER BY timeStamp DESC LIMIT 1")
    suspend fun getLatestTournament(): Tournament

    @Query("SELECT COUNT(*) FROM tournament")
    suspend fun getTournamentCount(): Int

    @Upsert
    suspend fun upsertTournament(tournament: Tournament)
}