package com.example.mtgtourney.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DeckOverview::class],
    version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun deckOverViewDao(): DeckOverviewDao
}