package com.example.mtgtourney.ui.match

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.collection.LruCache
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.mtgtourney.MainViewModel
import com.example.mtgtourney.R
import com.example.mtgtourney.data.Deck
import com.example.mtgtourney.data.Match
import com.example.mtgtourney.data.Tournament
import com.example.mtgtourney.databinding.FragmentCurrentMatchBinding
import com.example.mtgtourney.databinding.FragmentCurrentMatchLegacyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentMatchFragment : Fragment() {
    private val currentMatchViewModel: CurrentMatchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (currentMatchViewModel.match.value == null) {
            currentMatchViewModel.loadNextMatch(requireContext())
        }
        return inflater.inflate(R.layout.fragment_current_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            MaterialTheme { CurrentMatchScreen(currentMatchViewModel) }
        }
    }
}