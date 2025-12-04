package com.example.mtgtourney.ui.overview

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.mtgtourney.data.tournament.Match
import com.example.mtgtourney.databinding.FragmentRoundBinding

class RoundFragment: Fragment() {

    private var _binding: FragmentRoundBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var matches: List<Match>
    private var round: Int = 1

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
        binding.roundTitle.text = "Round " + round
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.setContent { MaterialTheme { MatchList(matches)} }
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