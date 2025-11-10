package com.example.mtgtourney.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class StatsRepository @Inject constructor() {
    suspend fun getStats(appContext: Context): List<DeckOverview> =
        withContext(Dispatchers.IO) {
            try {
                val statsFile =
                    appContext.openFileInput(STATS).bufferedReader().useLines { lines ->
                        lines.fold("") { start, end ->
                            "$start $end"
                        }
                    }
                val gson = Gson()
                gson.fromJson(statsFile, object : TypeToken<List<DeckOverview>>() {}.type)
            } catch (e: Exception) {
                listOf()
            }
        }

    /**
     * Recreate the tournament based on size
     */
    suspend fun updateStats(appContext: Context, stats: List<DeckOverview>) {
        withContext(Dispatchers.IO) {
            appContext.deleteFile(STATS)
            appContext.openFileOutput(STATS, Context.MODE_PRIVATE).use {
                it.write(Gson().toJson(stats).toByteArray())
                it.flush()
            }
        }
    }

}