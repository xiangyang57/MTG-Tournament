package com.example.mtgtourney.ui.overview

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtgtourney.R
import com.example.mtgtourney.data.Match
import com.example.mtgtourney.databinding.FragmentRoundBinding


class RoundFragment: Fragment() {

    private var _binding: FragmentRoundBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var matches: List<Match>
    private var round: Int = 1
    private lateinit var adapter: MatchItemAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matches = requireArguments().getParcelableArrayList(ARG_MATCHES, Match::class.java)!!
        round = requireArguments().getInt(ARG_ROUND)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoundBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = binding.roundMatchList
        binding.roundTitle.text = "Round " + round
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MatchItemAdapter(matches)
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.getContext(),
            LinearLayoutManager.VERTICAL // Use LinearLayoutManager.VERTICAL or HORIZONTAL
        )
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        recyclerView.addItemDecoration(dividerItemDecoration)
        return root
    }

    companion object {
        private const val ARG_MATCHES = "matches"
        private const val ARG_ROUND = "round"
        fun newInstance(matches: List<Match>, round: Int) = RoundFragment().apply {
            val bundle = bundleOf()
            bundle.putParcelableArrayList(ARG_MATCHES, ArrayList(matches))
            bundle.putInt(ARG_ROUND, round)
            arguments = bundle
        }
    }
}