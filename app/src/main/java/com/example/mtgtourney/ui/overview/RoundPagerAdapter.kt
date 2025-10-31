package com.example.mtgtourney.ui.overview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mtgtourney.data.Match

class RoundPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val rounds: MutableList<MutableList<Match>>
): FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return RoundFragment.newInstance(rounds[position], position+1)
    }

    override fun getItemCount() = rounds.size

    override fun getItemId(position: Int): Long {
        // return a unique stable ID for this page based on your data
        return rounds[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        // tell adapter which items are still valid
        return rounds.any { it.hashCode().toLong() == itemId }
    }
}