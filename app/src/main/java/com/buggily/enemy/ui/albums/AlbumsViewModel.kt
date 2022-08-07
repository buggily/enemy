package com.buggily.enemy.ui.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.domain.album.Album
import com.buggily.enemy.domain.use.album.GetAlbumsPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    getAlbumsPaging: GetAlbumsPaging,
) : ViewModel() {

    val albums: Flow<PagingData<Result<Album>>>

    private val _state: MutableStateFlow<AlbumsState>
    val state: StateFlow<AlbumsState> get() = _state

    init {
        AlbumsState.default.copy(
            searchState = AlbumsState.SearchState.default.copy(
                onSearchChange = ::onSearchChange,
            ),
        ).let { _state = MutableStateFlow(it) }

        albums = state.flatMapMerge {
            val searchState: AlbumsState.SearchState = it.searchState
            val search: String = searchState.search

            getAlbumsPaging(search)
        }.cachedIn(viewModelScope)
    }

    private fun onSearchChange(search: String) = setSearchOfSearchState(
        search = search,
    )

    private fun setSearchOfSearchState(search: String) = state.value.let {
        val searchState: AlbumsState.SearchState = it.searchState.copy(
            search = search,
        )

        setSearchState(searchState)
    }

    private fun setSearchState(searchState: AlbumsState.SearchState) = _state.update {
        it.copy(searchState = searchState)
    }
}
