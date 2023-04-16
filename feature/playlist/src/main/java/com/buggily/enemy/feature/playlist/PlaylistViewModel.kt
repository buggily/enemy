package com.buggily.enemy.feature.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.paging.PagingData
import com.buggily.enemy.core.ext.indexOfOrNull
import com.buggily.enemy.core.ui.ext.toMediaItem
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.domain.controller.PlayItems
import com.buggily.enemy.domain.playlist.GetPlaylistById
import com.buggily.enemy.domain.track.GetTrackPagingByPlaylistId
import com.buggily.enemy.domain.track.GetTracksByPlaylistId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playItems: PlayItems,
    private val getTracksByPlaylistId: GetTracksByPlaylistId,
    getPlaylistById: GetPlaylistById,
    getTrackPagingByPlaylistId: GetTrackPagingByPlaylistId,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val playlistId: Long

    private val _uiState: MutableStateFlow<PlaylistUiState>
    val uiState: StateFlow<PlaylistUiState> get() = _uiState

    val tracks: Flow<PagingData<Track>>

    init {
        PlaylistUiState.default.copy(
            trackState = PlaylistUiState.TrackState.default.copy(
                onClick = ::onTrackClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }

        playlistId = checkNotNull(savedStateHandle["id"])
        tracks = getTrackPagingByPlaylistId(playlistId)

        viewModelScope.launch {
            _uiState.update {
                it.copy(playlist = getPlaylistById(playlistId))
            }
        }
    }

    private fun onTrackClick(track: Track) = viewModelScope.launch {
        val tracks: List<Track> = getTracksByPlaylistId(playlistId)
        val index: Int = checkNotNull(tracks.indexOfOrNull(track))
        val items: List<MediaItem> = tracks.map { it.toMediaItem() }

        playItems(
            index = index,
            items = items,
        )
    }
}
