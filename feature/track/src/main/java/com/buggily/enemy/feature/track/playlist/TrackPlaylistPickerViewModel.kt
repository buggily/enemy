package com.buggily.enemy.feature.track.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.domain.navigation.NavigateBackFromTrackPlaylistPicker
import com.buggily.enemy.domain.playlist.GetPlaylistPaging
import com.buggily.enemy.domain.playlist.PlaylistUi
import com.buggily.enemy.domain.playlistWithTracks.InsertTrackByIdAndPlaylistId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackPlaylistPickerViewModel @Inject constructor(
    private val insertTrackByIdAndPlaylistId: InsertTrackByIdAndPlaylistId,
    private val navigateBackFromTrackPlaylistPicker: NavigateBackFromTrackPlaylistPicker,
    getPlaylistPaging: GetPlaylistPaging,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val trackId: Long

    private val _uiState: MutableStateFlow<TrackPlaylistPickerUiState>
    val uiState: StateFlow<TrackPlaylistPickerUiState> get() = _uiState

    val playlists: Flow<PagingData<PlaylistUi>> = getPlaylistPaging(String())

    init {
        val trackIdKey: String = NavigationDestination.Track.PlaylistPicker.TRACK_ID
        trackId = checkNotNull(savedStateHandle[trackIdKey])

        TrackPlaylistPickerUiState(
            playlistState = TrackPlaylistPickerUiState.PlaylistState(
                onClick = ::onPlaylistClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }
    }

    private fun onPlaylistClick(playlist: PlaylistUi) = viewModelScope.launch {
        insertTrackByIdAndPlaylistId(
            trackId = trackId,
            playlistId = playlist.id,
        )

        navigateBackFromTrackPlaylistPicker(trackId)
    }
}
