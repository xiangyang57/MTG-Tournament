package com.example.mtgtourney.ui.stats

import androidx.recyclerview.widget.RecyclerView
import com.example.mtgtourney.data.stats.DeckOverview
import com.example.mtgtourney.databinding.StatsCardBinding
import com.example.mtgtourney.ui.common.CommanderRes

class StatsItemCard(private val binding: StatsCardBinding,
    private val commanderRes: CommanderRes): RecyclerView.ViewHolder(binding.root) {
    fun bind(overview: DeckOverview) {
        binding.overview = overview
        binding.commanderImage.setImageResource(commanderRes.getCommanderRes(overview.deck.commander))
        binding.executePendingBindings()
    }
}