package com.example.mtgtourney.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mtgtourney.MainViewModel
import com.example.mtgtourney.R
import com.example.mtgtourney.data.Deck
import com.example.mtgtourney.data.Match
import com.example.mtgtourney.data.Tournament
import com.example.mtgtourney.databinding.FragmentCurrentMatchBinding

const val UNSELECTED = 0.8F
const val SELECTED = 1F
const val WIN_COUNT = 2

class CurrentMatchFragment : Fragment() {

    private var _binding: FragmentCurrentMatchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var selectedDeck: Deck? = null
    private var playerOneVictoryCount = 0
    private var playerTwoVictoryCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainViewModel =
            ViewModelProvider(activity as AppCompatActivity)[MainViewModel::class.java]

        _binding = FragmentCurrentMatchBinding.inflate(inflater, container, false)
        mainViewModel.tournamentLiveData.observe(viewLifecycleOwner) {
            refreshUI(it)
        }
         return binding.root
    }

    private fun refreshUI(tournament: Tournament) {
        binding.playerOneVictoryOne.setImageResource(R.drawable.victory_indicator)
        binding.playerOneVictoryTwo.setImageResource(R.drawable.victory_indicator)
        binding.playerTwoVictoryOne.setImageResource(R.drawable.victory_indicator)
        binding.playerTwoVictoryTwo.setImageResource(R.drawable.victory_indicator)
        binding.playerOneDeck.text = null
        binding.playerTwoDeck.text = null
        playerOneVictoryCount = 0
        playerTwoVictoryCount = 0
        unSelect()

        findNextMatch(tournament)?.let {
            binding.playerOneDeck.text = it.playerA.commander
            binding.playerTwoDeck.text = it.playerB?.commander
            setClicklistener(it, tournament)
        }

    }

    private fun updateTournament(match: Match, tournament: Tournament) {
        val mainViewModel =
            ViewModelProvider(activity as AppCompatActivity)[MainViewModel::class.java]

        val currentBracket = tournament.brackets[tournament.brackets.lastIndex]
        if (currentBracket.size > 1 && match == currentBracket[currentBracket.lastIndex]) {
            val nextBracket = mutableListOf<Match>()
            for (i in 1 until currentBracket.size step 2) {
                nextBracket.add(Match(currentBracket[i-1].winner!!, currentBracket[i].winner))
            }
            if (currentBracket.size % 2 == 1) {
                nextBracket.add(Match(currentBracket[currentBracket.size-1].winner!!))
            }
            tournament.brackets.add(nextBracket)
        }

        mainViewModel.updateTournament(requireContext(), tournament)
    }

    private fun findNextMatch(tournament: Tournament): Match? {
        for (i in tournament.brackets.indices) {
            for (j in tournament.brackets[i].indices) {
                if (tournament.brackets[i][j].winner == null &&
                    tournament.brackets[i][j].playerB != null) {
                    return tournament.brackets[i][j]
                }
            }
        }
        return null
    }

    private fun setClicklistener(match: Match, tournament: Tournament) {
        binding.playerOneCard.setOnClickListener {
            binding.playerOneCard.alpha = SELECTED
            binding.playerTwoCard.alpha = UNSELECTED
            binding.confirmVictor.isEnabled = true
            selectedDeck = match.playerA
        }

        binding.playerTwoCard.setOnClickListener {
            binding.playerOneCard.alpha = UNSELECTED
            binding.playerTwoCard.alpha = SELECTED
            binding.confirmVictor.isEnabled = true
            selectedDeck = match.playerB
        }

        binding.confirmVictor.setOnClickListener {
            selectedDeck?.let {
                if (it == match.playerA) {
                    playerOneVictoryCount++
                    if (playerOneVictoryCount == 1) {
                        binding.playerOneVictoryOne
                            .setImageResource(R.drawable.player_one_victory_indicator)
                    } else {
                        binding.playerOneVictoryTwo
                            .setImageResource(R.drawable.player_one_victory_indicator)
                    }
                } else {
                    playerTwoVictoryCount++
                    if (playerTwoVictoryCount == 1) {
                        binding.playerTwoVictoryOne
                            .setImageResource(R.drawable.player_two_victory_indicator)
                    } else {
                        binding.playerTwoVictoryTwo
                            .setImageResource(R.drawable.player_two_victory_indicator)
                    }
                }
                updateMatch(match, tournament)
                unSelect()
            }
        }
    }

    private fun unSelect() {
        binding.playerOneCard.alpha = UNSELECTED
        binding.playerTwoCard.alpha = UNSELECTED
        binding.confirmVictor.isEnabled = false
        selectedDeck = null
    }

    private fun updateMatch(match: Match, tournament: Tournament) {
        if (selectedDeck != null &&
            playerOneVictoryCount == WIN_COUNT ||
            playerTwoVictoryCount == WIN_COUNT) {
            match.winner = selectedDeck
            val loser = if (match.winner == match.playerA) match.playerB else match.playerA
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder
                .setMessage("${selectedDeck?.commander} has railed ${loser?.commander}")
                .setTitle("Victory")
                .setOnDismissListener{
                    updateTournament(match, tournament)
                    refreshUI(tournament)
                }
                .setPositiveButton("Next Match") { _, _ ->
                }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}