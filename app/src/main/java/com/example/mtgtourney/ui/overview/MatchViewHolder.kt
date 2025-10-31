package com.example.mtgtourney.ui.overview

import androidx.recyclerview.widget.RecyclerView
import com.example.mtgtourney.data.Match
import com.example.mtgtourney.databinding.MatchCardBinding

class MatchViewHolder(private val binding: MatchCardBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(match: Match) {
        binding.match = match
    }
}