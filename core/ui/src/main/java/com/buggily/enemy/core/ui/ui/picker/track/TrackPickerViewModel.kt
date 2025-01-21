package com.buggily.enemy.core.ui.ui.picker.track

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.core.ui.ui.picker.PickerUiState
import com.buggily.enemy.core.ui.ui.picker.PickerViewModel
import com.buggily.enemy.core.ui.ui.picker.Pickerable
import com.buggily.enemy.domain.navigation.NavigateToTrackPlaylistPicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackPickerViewModel @Inject constructor(
    private val navigateToTrackPlaylistPicker: NavigateToTrackPlaylistPicker,
    savedStateHandle: SavedStateHandle,
) : PickerViewModel() {

    private val trackId: Long

    private val _uiState: MutableStateFlow<PickerUiState>
    override val uiState: StateFlow<PickerUiState> get() = _uiState

    init {
        val trackIdKey: String = NavigationDestination.Track.Picker.TRACK_ID
        trackId = checkNotNull(savedStateHandle[trackIdKey])

        PickerUiState(
            values = TrackPicker.values,
            onClick = ::onPickerClick,
        ).let { _uiState = MutableStateFlow(it) }
    }

    override fun onPickerClick(picker: Pickerable) {
        when (picker) {
            is TrackPicker.Playlist -> viewModelScope.launch {
                navigateToTrackPlaylistPicker(trackId)
            }
            else -> Unit
        }
    }
}
