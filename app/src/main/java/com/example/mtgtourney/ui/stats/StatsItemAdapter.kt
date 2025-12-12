package com.example.mtgtourney.ui.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtgtourney.data.stats.DeckOverview
import com.example.mtgtourney.databinding.StatsCardBinding
import com.example.mtgtourney.ui.common.CommanderRes

class StatsItemAdapter(
    private val stats: List<DeckOverview>
): RecyclerView.Adapter<StatsItemCard>() {

    private val commanderRes = CommanderRes()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatsItemCard {
        val inflater = LayoutInflater.from(parent.context)
        return StatsItemCard(StatsCardBinding.inflate(inflater, parent, false), commanderRes)
    }

    override fun onBindViewHolder(
        holder: StatsItemCard,
        position: Int
    ) {
        holder.bind(stats[position])
    }

    override fun getItemCount() = stats.size
}