package com.example.mtgtourney.ui.stats

import androidx.recyclerview.widget.RecyclerView
import com.example.mtgtourney.data.DeckOverview
import com.example.mtgtourney.databinding.StatsCardBinding

class StatsItemCard (private val binding: StatsCardBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(overview: DeckOverview) {
        binding.overview = overview
    }
}