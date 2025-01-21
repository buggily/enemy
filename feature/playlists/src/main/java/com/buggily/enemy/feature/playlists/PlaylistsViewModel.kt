package com.buggily.enemy.feature.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buggily.enemy.core.ui.SearchableViewModel
import com.buggily.enemy.domain.navigation.NavigateToPlaylist
import com.buggily.enemy.domain.navigation.NavigateToPlaylistPicker
import com.buggily.enemy.domain.playlist.GetPlaylistPaging
import com.buggily.enemy.domain.playlist.PlaylistUi
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
class PlaylistsViewModel @Inject constructor(
    private val navigateToPlaylist: NavigateToPlaylist,
    private val navigateToPlaylistPicker: NavigateToPlaylistPicker,
    getPlaylistPaging: GetPlaylistPaging,
) : ViewModel(), SearchableViewModel {

    private val _uiState: MutableStateFlow<PlaylistsUiState>
    val uiState: StateFlow<PlaylistsUiState> get() = _uiState

    val playlists: Flow<PagingData<PlaylistUi>>

    init {
        PlaylistsUiState(
            playlistState = PlaylistsUiState.PlaylistState(
                onClick = ::onPlaylistClick,
                onLongClick = ::onPlaylistLongClick,
            ),
            searchState = PlaylistsUiState.SearchState(
                value = String(),
            ),
        ).let { _uiState = MutableStateFlow(it) }

        val searchState: Flow<PlaylistsUiState.SearchState> = uiState.map {
            it.searchState
        }

        val search: Flow<String> = searchState.map {
            it.value
        }.distinctUntilChanged()

        playlists = search.flatMapLatest { getPlaylistPaging(it) }.cachedIn(viewModelScope)
    }

    override fun onSearchChange(value: String) = _uiState.update {
        it.copy(searchState = it.searchState.copy(value = value))
    }

    private fun onPlaylistClick(playlist: PlaylistUi) = navigateToPlaylist(
        playlistId = playlist.id,
    )

    private fun onPlaylistLongClick(playlist: PlaylistUi) = navigateToPlaylistPicker(
        playlistId = playlist.id,
    )
}
