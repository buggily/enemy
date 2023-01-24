package com.buggily.enemy.feature.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.core.model.track.TrackUi
import com.buggily.enemy.domain.album.GetAlbumByAlbumId
import com.buggily.enemy.domain.track.GetTracksByAlbumIdPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getAlbumByAlbumId: GetAlbumByAlbumId,
    getTracksByAlbumIdPaging: GetTracksByAlbumIdPaging,
) : ViewModel() {

    val tracks: Flow<PagingData<TrackUi>>

    private val _state: MutableStateFlow<AlbumState>
    val state: StateFlow<AlbumState> get() = _state

    init {
        val albumId: Long = checkNotNull(savedStateHandle["id"])

        tracks = getTracksByAlbumIdPaging(albumId).map { pagingData: PagingData<Track> ->
            pagingData.map { TrackUi.Item(it) }
        }.map {
            it.insertSeparators { before: TrackUi.Item?, after: TrackUi.Item? ->
                val beforeDisc: Int = before?.track?.position?.disc ?: return@insertSeparators null
                val afterDisc: Int = after?.track?.position?.disc ?: return@insertSeparators null
                if (afterDisc > beforeDisc) TrackUi.Separator.Disc(afterDisc) else null
            }
        }.cachedIn(viewModelScope)

        AlbumState.default.copy(
            album = getAlbumByAlbumId(albumId),
        ).let { _state = MutableStateFlow(it) }
    }

    companion object {
        const val id = "id"
    }
}
