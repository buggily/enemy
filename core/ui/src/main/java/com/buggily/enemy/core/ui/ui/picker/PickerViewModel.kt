package com.buggily.enemy.core.ui.ui.picker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class PickerViewModel : ViewModel() {
    abstract val uiState: StateFlow<PickerUiState>
    abstract fun onPickerClick(picker: Pickerable)
}
