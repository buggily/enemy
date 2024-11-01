package com.buggily.enemy.feature.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.paging.PagingData
import com.buggily.enemy.core.ext.firstIndex
import com.buggily.enemy.core.ext.indexOfOrNull
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.domain.controller.PlayItems
import com.buggily.enemy.domain.navigation.NavigateToPlaylistTrackPicker
import com.buggily.enemy.domain.playlist.GetPlaylistById
import com.buggily.enemy.domain.playlist.PlaylistUi
import com.buggily.enemy.domain.track.TrackWithIndexUi
import com.buggily.enemy.domain.track.playlist.GetTrackPagingByPlaylistId
import com.buggily.enemy.domain.track.playlist.GetTracksByPlaylistId
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
    private val navigateToPlaylistTrackPicker: NavigateToPlaylistTrackPicker,
    getPlaylistById: GetPlaylistById,
    getTrackPagingByPlaylistId: GetTrackPagingByPlaylistId,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val playlistId: Long

    private val _uiState: MutableStateFlow<PlaylistUiState>
    val uiState: StateFlow<PlaylistUiState> get() = _uiState

    val tracks: Flow<PagingData<TrackWithIndexUi>>

    init {
        val playlistIdKey: String = NavigationDestination.Playlist.PLAYLIST_ID
        playlistId = checkNotNull(savedStateHandle[playlistIdKey])
        tracks = getTrackPagingByPlaylistId(playlistId)

        PlaylistUiState(
            playlistState = PlaylistUiState.PlaylistState(
                playlist = null,
                onStartClick = ::onStartClick,
            ),
            trackState = PlaylistUiState.TrackState(
                track = null,
                onClick = ::onTrackClick,
                onLongClick = ::onTrackLongClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }

        viewModelScope.launch {
            val playlist: PlaylistUi? = getPlaylistById(playlistId)
            _uiState.update { it.copy(playlistState = it.playlistState.copy(playlist = playlist)) }
        }
    }

    private fun onStartClick() = viewModelScope.launch {
        val tracks: List<TrackWithIndexUi> = getTracksByPlaylistId(playlistId)
        val items: List<MediaItem> = tracks.map { it.track.toMediaItem() }

        playItems(
            index = tracks.firstIndex,
            items = items,
        )
    }

    private fun onTrackClick(
        track: TrackWithIndexUi,
    ) = viewModelScope.launch {
        val tracks: List<TrackWithIndexUi> = getTracksByPlaylistId(playlistId)
        val index: Int = tracks.indexOfOrNull(track) ?: return@launch
        val items: List<MediaItem> = tracks.map { it.track.toMediaItem() }

        playItems(
            index = index,
            items = items,
        )
    }

    private fun onTrackLongClick(
        track: TrackWithIndexUi,
    ) = navigateToPlaylistTrackPicker(
        trackId = track.track.id,
        playlistId = playlistId,
        trackIndex = track.index,
    )
}
