package com.example.mtgtourney.ui.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mtgtourney.data.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    val statsRepository: StatsRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This page is under construction"
    }
    val text: LiveData<String> = _text


}