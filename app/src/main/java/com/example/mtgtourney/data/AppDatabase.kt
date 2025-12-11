package com.example.mtgtourney.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mtgtourney.data.stats.DeckOverview
import com.example.mtgtourney.data.stats.DeckOverviewDao
import com.example.mtgtourney.data.tournament.Tournament
import com.example.mtgtourney.data.tournament.TournamentDao

@Database(entities = [DeckOverview::class, Tournament::class],
    version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun deckOverViewDao(): DeckOverviewDao

    abstract fun tournamentDao(): TournamentDao
}