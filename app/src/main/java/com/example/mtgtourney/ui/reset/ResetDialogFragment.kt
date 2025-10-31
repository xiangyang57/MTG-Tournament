package com.example.mtgtourney.ui.reset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mtgtourney.MainViewModel
import com.example.mtgtourney.databinding.FragmentResetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ResetDialogFragment: BottomSheetDialogFragment() {
    private var _binding: FragmentResetDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetDialogBinding.inflate(inflater, container, false)
        val mainViewModel =
            ViewModelProvider(activity as AppCompatActivity)[MainViewModel::class.java]
        val resetViewModel =
            ViewModelProvider(this)[ResetDialogViewModel::class.java]
        binding.viewModel = resetViewModel

        resetViewModel.selectionConfirmed.observe(viewLifecycleOwner) { selected ->
            selected?.let {
                mainViewModel.resetTournament(requireContext(), it)
                dismiss()
            }
        }
        binding.radioGroup.setOnCheckedChangeListener { _, checkId->
            resetViewModel.selectedOptionId.value = checkId
        }
        binding.closeButton.setOnClickListener { v -> dismiss() }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}