package com.buggily.enemy.ui.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.domain.album.Album
import com.buggily.enemy.domain.search.Search
import com.buggily.enemy.domain.use.album.GetAlbumsPaging
import com.buggily.enemy.domain.use.search.GetSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    getAlbumsPaging: GetAlbumsPaging,
    getSearch: GetSearch,
) : ViewModel() {

    val albums: Flow<PagingData<Result<Album>>>

    private val _state: MutableStateFlow<AlbumsState>
    private val state: StateFlow<AlbumsState> get() = _state

    init {
        AlbumsState.default.let { _state = MutableStateFlow(it) }

        val searchState: Flow<AlbumsState.SearchState> = state.map {
            it.searchState
        }

        val search: Flow<Search> = searchState.map {
            it.search
        }

        albums = search.flatMapLatest {
            getAlbumsPaging(it.value)
        }.cachedIn(viewModelScope)

        viewModelScope.launch {
            getSearch().debounce(1000).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = AlbumsState.SearchState.default.search,
            ).collectLatest { onSearchChange(it) }
        }
    }

    private fun onSearchChange(search: Search) = setSearchOfSearchState(
        search = search,
    )

    private fun setSearchOfSearchState(search: Search) = state.value.let {
        val searchState: AlbumsState.SearchState = it.searchState.copy(
            search = search,
        )

        setSearchState(searchState)
    }

    private fun setSearchState(searchState: AlbumsState.SearchState) = _state.update {
        it.copy(searchState = searchState)
    }
}
