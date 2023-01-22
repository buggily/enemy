package com.buggily.enemy.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.core.domain.GetTimeOfDay
import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.domain.album.GetAlbumsPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    getAlbumsPaging: GetAlbumsPaging,
    getTimeOfDay: GetTimeOfDay,
) : ViewModel() {

    private val _state: MutableStateFlow<AlbumsState>
    val state: StateFlow<AlbumsState> get() = _state

    init {
        AlbumsState.default.copy(
            timeOfDay = getTimeOfDay(),
        ).let { _state = MutableStateFlow(it) }
    }

    val albums: Flow<PagingData<Album>> = getAlbumsPaging(String()).cachedIn(viewModelScope)
}
