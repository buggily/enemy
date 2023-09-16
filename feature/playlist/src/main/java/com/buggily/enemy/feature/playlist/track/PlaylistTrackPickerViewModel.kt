package com.buggily.enemy.feature.playlist.track

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.core.ui.ui.picker.PickerUiState
import com.buggily.enemy.core.ui.ui.picker.PickerViewModel
import com.buggily.enemy.core.ui.ui.picker.Pickerable
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.domain.navigation.NavigateBack
import com.buggily.enemy.domain.track.GetTrackByPlaylistIdAndIndex
import com.buggily.enemy.domain.track.RemoveTrackByPlaylistId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistTrackPickerViewModel @Inject constructor(
    private val getTrackByPlaylistIdAndIndex: GetTrackByPlaylistIdAndIndex,
    private val removeTrackByPlaylistId: RemoveTrackByPlaylistId,
    private val navigateBack: NavigateBack,
    savedStateHandle: SavedStateHandle,
) : PickerViewModel() {

    private val playlistId: Long
    private val trackIndex: Int

    private val _uiState: MutableStateFlow<PickerUiState>
    override val uiState: StateFlow<PickerUiState> get() = _uiState

    init {
        val playlistIdKey: String = NavigationDestination.Playlist.TrackPicker.playlistId
        val trackIndexKey: String = NavigationDestination.Playlist.TrackPicker.trackIndex

        playlistId = checkNotNull(savedStateHandle[playlistIdKey])
        trackIndex = checkNotNull(savedStateHandle[trackIndexKey])

        PickerUiState(
            values = PlaylistTrackPicker.values,
            onClick = ::onPickerClick,
        ).let { _uiState = MutableStateFlow(it) }
    }

    override fun onPickerClick(picker: Pickerable) {
        when (picker) {
            is PlaylistTrackPicker.Remove -> viewModelScope.launch {
                val track: TrackWithIndex = getTrackByPlaylistIdAndIndex(
                    playlistId = playlistId,
                    index = trackIndex,
                ) ?: return@launch

                removeTrackByPlaylistId(
                    playlistId = playlistId,
                    track = track,
                )

                navigateBack()
            }
            else -> Unit
        }
    }
}
