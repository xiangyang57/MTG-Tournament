package com.example.mtgtourney.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeckOverviewDao {

    @Query("SELECT * FROM deckOverview")
    suspend fun getAllDeckOverviews(): List<DeckOverview>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDeckOverView(deckOverview: DeckOverview)

    @Update
    suspend fun updateDeckOverView(deckOverview: DeckOverview)
}