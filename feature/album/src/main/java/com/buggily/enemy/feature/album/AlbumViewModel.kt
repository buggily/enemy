package com.buggily.enemy.feature.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.buggily.enemy.core.ext.indexOfOrNull
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.core.ui.ext.toMediaItem
import com.buggily.enemy.core.ui.model.TrackUi
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.domain.album.GetAlbumById
import com.buggily.enemy.domain.controller.PlayItems
import com.buggily.enemy.domain.navigation.NavigateToTrackPicker
import com.buggily.enemy.domain.track.GetTrackPagingByAlbumId
import com.buggily.enemy.domain.track.GetTracksByAlbumId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val playItems: PlayItems,
    private val getTracksByAlbumId: GetTracksByAlbumId,
    private val navigateToTrackPicker: NavigateToTrackPicker,
    getAlbumById: GetAlbumById,
    getTrackPagingByAlbumId: GetTrackPagingByAlbumId,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val albumId: Long

    private val _uiState: MutableStateFlow<AlbumUiState>
    val uiState: StateFlow<AlbumUiState> get() = _uiState

    val tracks: Flow<PagingData<TrackUi>>

    init {
        val albumIdKey: String = NavigationDestination.Album.albumId
        albumId = checkNotNull(savedStateHandle[albumIdKey])

        AlbumUiState(
            album = null,
            trackState = AlbumUiState.TrackState(
                onClick = ::onTrackClick,
                onLongClick = ::onTrackLongClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }

        tracks = getTrackPagingByAlbumId(albumId).map { pagingData: PagingData<Track> ->
            pagingData.map { TrackUi.Item(it) }
        }.map {
            it.insertSeparators { before: TrackUi.Item?, after: TrackUi.Item? ->
                val beforeDisc: Int = before?.track?.position?.disc ?: return@insertSeparators null
                val afterDisc: Int = after?.track?.position?.disc ?: return@insertSeparators null
                if (beforeDisc >= afterDisc) return@insertSeparators null

                TrackUi.Separator.Disc(afterDisc)
            }
        }.cachedIn(viewModelScope)

        viewModelScope.launch {
            _uiState.update { it.copy(album = getAlbumById(albumId)) }
        }
    }

    private fun onTrackClick(track: Track) = viewModelScope.launch {
        val tracks: List<Track> = getTracksByAlbumId(albumId)
        val index: Int = checkNotNull(tracks.indexOfOrNull(track))
        val items: List<MediaItem> = tracks.map { it.toMediaItem() }

        playItems(
            index = index,
            items = items,
        )
    }

    private fun onTrackLongClick(track: Track) = navigateToTrackPicker(
        trackId = track.id,
    )
}
