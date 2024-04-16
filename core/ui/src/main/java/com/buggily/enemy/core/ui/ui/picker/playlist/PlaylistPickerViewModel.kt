package com.buggily.enemy.core.ui.ui.picker.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.core.ui.ui.picker.PickerUiState
import com.buggily.enemy.core.ui.ui.picker.PickerViewModel
import com.buggily.enemy.core.ui.ui.picker.Pickerable
import com.buggily.enemy.domain.navigation.NavigateBackFromPlaylistPicker
import com.buggily.enemy.domain.navigation.NavigateToEditPlaylist
import com.buggily.enemy.domain.playlistWithTracks.DeletePlaylistById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistPickerViewModel @Inject constructor(
    private val deletePlaylistById: DeletePlaylistById,
    private val navigateToEditPlaylist: NavigateToEditPlaylist,
    private val navigateBackFromPlaylistPicker: NavigateBackFromPlaylistPicker,
    savedStateHandle: SavedStateHandle,
) : PickerViewModel() {

    private val playlistId: Long

    private val _uiState: MutableStateFlow<PickerUiState>
    override val uiState: StateFlow<PickerUiState> get() = _uiState

    init {
        val playlistIdKey: String = NavigationDestination.Playlist.Picker.PLAYLIST_ID
        playlistId = checkNotNull(savedStateHandle[playlistIdKey])

        PickerUiState(
            values = PlaylistPicker.values,
            onClick = ::onPickerClick,
        ).let { _uiState = MutableStateFlow(it) }
    }

    override fun onPickerClick(picker: Pickerable) {
        when (picker) {
            is PlaylistPicker.Edit -> viewModelScope.launch {
                navigateToEditPlaylist(playlistId)
            }
            is PlaylistPicker.Delete -> viewModelScope.launch {
                deletePlaylistById(playlistId)
                navigateBackFromPlaylistPicker(playlistId)
            }
            else -> Unit
        }
    }
}
