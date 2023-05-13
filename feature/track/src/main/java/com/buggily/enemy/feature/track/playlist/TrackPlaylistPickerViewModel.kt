package com.buggily.enemy.feature.track.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.domain.navigation.NavigateBack
import com.buggily.enemy.domain.playlist.GetPlaylistPaging
import com.buggily.enemy.domain.track.GetTrackById
import com.buggily.enemy.domain.track.InsertTrackByPlaylistId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackPlaylistPickerViewModel @Inject constructor(
    private val getTrackById: GetTrackById,
    private val insertTrackByPlaylistId: InsertTrackByPlaylistId,
    private val navigateBack: NavigateBack,
    getPlaylistPaging: GetPlaylistPaging,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val trackId: Long

    private val _uiState: MutableStateFlow<TrackPlaylistPickerUiState>
    val uiState: StateFlow<TrackPlaylistPickerUiState> get() = _uiState

    val playlists: Flow<PagingData<Playlist>> = getPlaylistPaging(String())

    init {
        val trackIdKey: String = NavigationDestination.Track.PlaylistPicker.trackId
        trackId = checkNotNull(savedStateHandle[trackIdKey])

        TrackPlaylistPickerUiState.default.copy(
            playlistState = TrackPlaylistPickerUiState.PlaylistState.default.copy(
                onClick = ::onPlaylistClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }
    }

    private fun onPlaylistClick(playlist: Playlist) = viewModelScope.launch {
        val track: Track = getTrackById(trackId) ?: return@launch

        insertTrackByPlaylistId(
            track = track,
            playlistId = playlist.id,
        )

        navigateBack()
    }
}
