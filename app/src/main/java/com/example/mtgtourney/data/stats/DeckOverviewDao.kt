package com.example.mtgtourney.data.stats

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface DeckOverviewDao {

    @Query("SELECT * FROM deckOverview")
    suspend fun getAllDeckOverviews(): List<DeckOverview>

    @Upsert
    suspend fun upsertDeckOverView(deckOverview: DeckOverview)
}