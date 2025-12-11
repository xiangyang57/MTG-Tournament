package com.example.mtgtourney.data

import android.content.Context
import androidx.room.Room
import com.example.mtgtourney.data.stats.DeckOverviewDao
import com.example.mtgtourney.data.tournament.TournamentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "appDatabase"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun providesDeckOverViewDao(database: AppDatabase): DeckOverviewDao = database.deckOverViewDao()

    @Provides
    fun providesTournamentDao(database: AppDatabase): TournamentDao = database.tournamentDao()
}