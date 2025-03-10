package com.buggily.enemy.feature.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.buggily.enemy.core.ext.firstIndex
import com.buggily.enemy.core.ext.indexOfOrNull
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.domain.album.AlbumUi
import com.buggily.enemy.domain.album.GetAlbumById
import com.buggily.enemy.domain.controller.PlayItems
import com.buggily.enemy.domain.navigation.NavigateToTrackPicker
import com.buggily.enemy.domain.resume.ResumeUi
import com.buggily.enemy.domain.resume.SetResume
import com.buggily.enemy.domain.track.GetTrackPagingByAlbumId
import com.buggily.enemy.domain.track.GetTracksByAlbumId
import com.buggily.enemy.domain.track.TrackUi
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
    private val setResume: SetResume,
    private val getTracksByAlbumId: GetTracksByAlbumId,
    private val navigateToTrackPicker: NavigateToTrackPicker,
    getAlbumById: GetAlbumById,
    getTrackPagingByAlbumId: GetTrackPagingByAlbumId,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val albumId: Long

    private val _uiState: MutableStateFlow<AlbumUiState>
    val uiState: StateFlow<AlbumUiState> get() = _uiState

    val tracks: Flow<PagingData<TrackAlbumUi>>

    init {
        val albumIdKey: String = NavigationDestination.Album.ALBUM_ID
        albumId = checkNotNull(savedStateHandle[albumIdKey])

        AlbumUiState(
            albumState = AlbumUiState.AlbumState(
                album = null,
                onStartClick = ::onStartClick,
            ),
            trackState = AlbumUiState.TrackState(
                onClick = ::onTrackClick,
                onLongClick = ::onTrackLongClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }

        tracks = getTrackPagingByAlbumId(albumId).map { pagingData: PagingData<TrackUi> ->
            pagingData.map { TrackAlbumUi.Item(it) }
        }.map {
            it.insertSeparators { before: TrackAlbumUi.Item?, after: TrackAlbumUi.Item? ->
                val beforeDisc: Int = before?.track?.position?.disc ?: return@insertSeparators null
                val afterDisc: Int = after?.track?.position?.disc ?: return@insertSeparators null
                if (beforeDisc >= afterDisc) return@insertSeparators null

                TrackAlbumUi.Separator.Disc(afterDisc)
            }
        }.cachedIn(viewModelScope)

        viewModelScope.launch {
            val album: AlbumUi? = getAlbumById(albumId)
            _uiState.update { it.copy(albumState = it.albumState.copy(album = album)) }
        }
    }

    private fun onStartClick() = viewModelScope.launch {
        val tracks: List<TrackUi> = getTracksByAlbumId(albumId)
        val items: List<MediaItem> = tracks.map { it.toMediaItem() }

        playItemsAndSetResume(
            index = tracks.firstIndex,
            items = items,
        )
    }

    private fun onTrackClick(track: TrackUi) = viewModelScope.launch {
        val tracks: List<TrackUi> = getTracksByAlbumId(albumId)
        val index: Int = tracks.indexOfOrNull(track) ?: return@launch
        val items: List<MediaItem> = tracks.map { it.toMediaItem() }

        playItemsAndSetResume(
            index = index,
            items = items,
        )
    }

    private fun onTrackLongClick(track: TrackUi) = navigateToTrackPicker(
        trackId = track.id,
    )

    private fun playItemsAndSetResume(
        index: Int,
        items: List<MediaItem>,
    ) = viewModelScope.launch {
        playItems(
            index = index,
            items = items,
        )

        setResume(
            id = albumId,
            source = ResumeUi.Source.Album(index),
        )
    }
}
