package com.buggily.enemy.feature.playlists

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.buggily.enemy.core.ui.SearchableViewModel
import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.domain.navigation.NavigateFromPlaylistsToPlaylist
import com.buggily.enemy.domain.playlist.GetPlaylistPaging
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
    private val navigateFromPlaylistsToPlaylist: NavigateFromPlaylistsToPlaylist,
    getPlaylistPaging: GetPlaylistPaging,
) : ViewModel(), SearchableViewModel {

    private val _uiState: MutableStateFlow<PlaylistsUiState>
    val uiState: StateFlow<PlaylistsUiState> get() = _uiState

    val playlists: Flow<PagingData<Playlist>>

    init {
        PlaylistsUiState.default.copy(
            playlistState = PlaylistsUiState.PlaylistState.default.copy(
                onClick = ::onPlaylistClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }

        val searchState: Flow<PlaylistsUiState.SearchState> = uiState.map {
            it.searchState
        }

        val search: Flow<String> = searchState.map {
            it.value
        }.distinctUntilChanged()

        playlists = search.flatMapLatest { getPlaylistPaging(it) }
    }

    override fun onSearchChange(value: String) = _uiState.update {
        it.copy(searchState = it.searchState.copy(value = value))
    }

    private fun onPlaylistClick(playlist: Playlist) = navigateFromPlaylistsToPlaylist(
        id = playlist.id,
    )
}
