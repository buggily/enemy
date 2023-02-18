package com.buggily.enemy.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.domain.album.GetAlbumsPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    getAlbumsPaging: GetAlbumsPaging,
) : ViewModel() {

    private val _state: MutableStateFlow<AlbumsState> = MutableStateFlow(AlbumsState.default)
    private val state: StateFlow<AlbumsState> get() = _state

    val albums: Flow<PagingData<Album>>

    init {
        val searchState: Flow<AlbumsState.SearchState> = state.map {
            it.searchState
        }

        albums = searchState.flatMapMerge {
            getAlbumsPaging(it.value)
        }.cachedIn(viewModelScope)
    }

    fun setSearch(value: String) = state.value.let {
        val searchState: AlbumsState.SearchState = it.searchState.copy(
            value = value,
        )

        setSearchState(searchState)
    }

    private fun setSearchState(searchState: AlbumsState.SearchState) = _state.update {
        it.copy(searchState = searchState)
    }
}
