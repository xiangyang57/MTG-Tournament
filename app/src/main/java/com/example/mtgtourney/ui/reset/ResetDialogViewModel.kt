package com.example.mtgtourney.ui.reset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mtgtourney.R

class ResetDialogViewModel: ViewModel() {

    // Keep track of selected radio button ID
    val selectedOptionId = MutableLiveData(R.id.option2)

    // Event to signal dismiss or action
    val selectionConfirmed = MutableLiveData<Int?>()

    fun onConfirmClicked() {
        val selected = when (selectedOptionId.value) {
            R.id.option1 -> 2
            R.id.option2 -> 4
            R.id.option3 -> 8
            R.id.option4 -> 16
            R.id.option5 -> 32
            else -> 2
        }
        selectionConfirmed.value = selected
    }
}