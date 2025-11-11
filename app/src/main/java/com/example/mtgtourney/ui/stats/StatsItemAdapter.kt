package com.example.mtgtourney.ui.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtgtourney.data.stats.DeckOverview
import com.example.mtgtourney.databinding.StatsCardBinding

class StatsItemAdapter(
    private val stats: List<DeckOverview>
): RecyclerView.Adapter<StatsItemCard>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatsItemCard {
        val inflater = LayoutInflater.from(parent.context)
        return StatsItemCard(StatsCardBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(
        holder: StatsItemCard,
        position: Int
    ) {
        holder.bind(stats[position])
    }

    override fun getItemCount() = stats.size
}