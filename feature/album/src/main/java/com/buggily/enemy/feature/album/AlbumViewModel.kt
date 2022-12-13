package com.buggily.enemy.feature.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.domain.album.GetAlbumByAlbumId
import com.buggily.enemy.domain.track.GetTracksByAlbumIdPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getAlbumByAlbumId: GetAlbumByAlbumId,
    getTracksByAlbumIdPaging: GetTracksByAlbumIdPaging,
) : ViewModel() {

    val tracks: Flow<PagingData<Track>>

    private val _state: MutableStateFlow<AlbumState>
    val state: StateFlow<AlbumState> get() = _state

    init {
        val albumId: Long = checkNotNull(savedStateHandle["id"])
        tracks = getTracksByAlbumIdPaging(albumId).cachedIn(viewModelScope)

        AlbumState.default.copy(
            album = getAlbumByAlbumId(albumId),
        ).let { _state = MutableStateFlow(it) }
    }

    companion object {
        const val id = "id"
    }
}
