package com.example.mtgtourney.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mtgtourney.MainViewModel
import com.example.mtgtourney.data.Tournament
import com.example.mtgtourney.databinding.FragmentDashboardBinding

class OverviewFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainViewModel =
            ViewModelProvider(activity as AppCompatActivity)[MainViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.reset.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder
                .setMessage("Are you sure you want to reset the tournament?")
                .setPositiveButton("Yes") { _, _ ->
                    mainViewModel.resetTournament(requireContext())
                }
                .setNegativeButton("No") {_,_ ->}
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
        mainViewModel.tournamentLiveData.observe(viewLifecycleOwner) {
            binding.textDashboard.text = getOverviewText(it)
        }
        return root
    }

    fun getOverviewText(tournament: Tournament): String {
        val builder = StringBuilder()
        for (i in tournament.brackets.indices) {
            builder.append("\n----------------Round ${i+1}----------------\n\n")
            for (j in tournament.brackets[i].indices) {
                val match = tournament.brackets[i][j]
                builder.append("${match.playerA.commander} vs ${match.playerB?.commander}\n" +
                        "Winner: ${match.winner?.commander}\n\n")
            }
        }
        return builder.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}