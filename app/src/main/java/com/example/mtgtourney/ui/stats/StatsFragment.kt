package com.example.mtgtourney.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtgtourney.R
import com.example.mtgtourney.databinding.FragmentStatsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val statsViewModel: StatsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.statsList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.getContext(),
            LinearLayoutManager.VERTICAL
        )
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        recyclerView.addItemDecoration(dividerItemDecoration)
        statsViewModel.stats.observe(viewLifecycleOwner) {
            val adapter = StatsItemAdapter(it)
            recyclerView.adapter = adapter
        }
        statsViewModel.getStats(requireContext())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}