package com.example.mtgtourney.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtgtourney.data.Match
import com.example.mtgtourney.databinding.MatchCardBinding

class MatchItemAdapter(
    private val matches: List<Match>
): RecyclerView.Adapter<MatchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MatchViewHolder(MatchCardBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(
        holder: MatchViewHolder,
        position: Int
    ) {
        holder.bind(matches[position])
    }

    override fun getItemCount() = matches.size
}