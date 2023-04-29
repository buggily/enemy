package com.buggily.enemy.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.core.ui.SearchableViewModel
import com.buggily.enemy.data.album.Album
import com.buggily.enemy.domain.album.GetAlbumPaging
import com.buggily.enemy.domain.navigation.NavigateToAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val navigateToAlbum: NavigateToAlbum,
    getAlbumPaging: GetAlbumPaging,
) : ViewModel(), SearchableViewModel {

    private val _uiState: MutableStateFlow<AlbumsUiState>
    val uiState: StateFlow<AlbumsUiState> get() = _uiState

    val albums: Flow<PagingData<Album>>

    init {
        AlbumsUiState.default.copy(
            albumState = AlbumsUiState.AlbumState.default.copy(
                onClick = ::onAlbumClick,
            )
        ).let { _uiState = MutableStateFlow(it) }

        val searchState: Flow<AlbumsUiState.SearchState> = uiState.map {
            it.searchState
        }

        val search: Flow<String> = searchState.map {
            it.value
        }.distinctUntilChanged()

        albums = search.flatMapLatest { getAlbumPaging(it) }.cachedIn(viewModelScope)
    }

    override fun onSearchChange(value: String) = _uiState.update {
        it.copy(searchState = it.searchState.copy(value = value))
    }

    private fun onAlbumClick(
        album: Album,
    ) = navigateToAlbum(
        albumId = album.id,
    )
}
