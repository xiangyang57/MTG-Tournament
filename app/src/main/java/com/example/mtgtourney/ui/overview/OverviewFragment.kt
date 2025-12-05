package com.example.mtgtourney.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.example.mtgtourney.MainViewModel
import com.example.mtgtourney.databinding.FragmentDashboardBinding
import com.example.mtgtourney.ui.reset.ResetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: RoundPagerAdapter? = null
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.reset.setOnClickListener {
            val bottomSheet = ResetDialogFragment()

            bottomSheet.show(parentFragmentManager, "ResetDialogFragment")
        }
        binding.viewpager.setSaveEnabled(false)
        mainViewModel.initTournament()
        mainViewModel.tournamentLiveData.observe(viewLifecycleOwner) {
            adapter = RoundPagerAdapter(activity as FragmentActivity, it.brackets)
            binding.viewpager.adapter = null
            binding.viewpager.post { binding.viewpager.adapter = adapter
                binding.viewpager.post {
                    binding.viewpager.currentItem = it.brackets.size-1
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}