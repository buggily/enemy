package com.buggily.enemy.feature.playlist.track

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.core.ui.ui.picker.PickerUiState
import com.buggily.enemy.core.ui.ui.picker.PickerViewModel
import com.buggily.enemy.core.ui.ui.picker.Pickerable
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.domain.navigation.NavigateBackFromPlaylistTrackPicker
import com.buggily.enemy.domain.playlistWithTracks.DeleteTrackByPlaylistId
import com.buggily.enemy.domain.track.playlist.GetTrackByPlaylistIdAndIndex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistTrackPickerViewModel @Inject constructor(
    private val getTrackByPlaylistIdAndIndex: GetTrackByPlaylistIdAndIndex,
    private val deleteTrackByPlaylistId: DeleteTrackByPlaylistId,
    private val navigateBackFromPlaylistTrackPicker: NavigateBackFromPlaylistTrackPicker,
    savedStateHandle: SavedStateHandle,
) : PickerViewModel() {

    private val playlistId: Long
    private val trackIndex: Int

    private val _uiState: MutableStateFlow<PickerUiState>
    override val uiState: StateFlow<PickerUiState> get() = _uiState

    init {
        val playlistIdKey: String = NavigationDestination.Playlist.TrackPicker.PLAYLIST_ID
        val trackIndexKey: String = NavigationDestination.Playlist.TrackPicker.TRACK_INDEX

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

                deleteTrackByPlaylistId(
                    playlistId = playlistId,
                    track = track,
                )

                navigateBackFromPlaylistTrackPicker(
                    playlistId = playlistId,
                    trackIndex = trackIndex,
                )
            }

            else -> Unit
        }
    }
}
