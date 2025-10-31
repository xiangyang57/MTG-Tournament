package com.example.mtgtourney.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mtgtourney.MainViewModel
import com.example.mtgtourney.databinding.FragmentDashboardBinding
import com.example.mtgtourney.ui.reset.ResetDialogFragment

class OverviewFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: RoundPagerAdapter? = null

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
            val bottomSheet = ResetDialogFragment()

            // Show the BottomSheet using parentFragmentManager
            bottomSheet.show(parentFragmentManager, "ResetDialogFragment")

        }
        binding.viewpager.setSaveEnabled(false)
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