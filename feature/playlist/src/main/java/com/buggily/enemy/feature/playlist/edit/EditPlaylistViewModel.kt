package com.buggily.enemy.feature.playlist.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.domain.navigation.NavigateBackFromEditPlaylist
import com.buggily.enemy.domain.playlist.GetPlaylistById
import com.buggily.enemy.domain.playlist.UpdatePlaylistById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPlaylistViewModel @Inject constructor(
    private val getPlaylistById: GetPlaylistById,
    private val updatePlaylistById: UpdatePlaylistById,
    private val navigateBackFromEditPlaylist: NavigateBackFromEditPlaylist,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val playlistId: Long

    private val _uiState: MutableStateFlow<EditPlaylistUiState>
    val uiState: StateFlow<EditPlaylistUiState> get() = _uiState

    init {
        val playlistIdKey: String = NavigationDestination.Playlist.Edit.PLAYLIST_ID
        playlistId = checkNotNull(savedStateHandle[playlistIdKey])

        EditPlaylistUiState(
            nameState = EditPlaylistUiState.NameState(
                value = String(),
                onChange = ::onNameChange,
            ),
            confirmState = EditPlaylistUiState.ConfirmState(
                isEnabled = false,
                onClick = ::onConfirmClick,
            )
        ).let { _uiState = MutableStateFlow(it) }

        val nameState: Flow<EditPlaylistUiState.NameState> = uiState.map {
            it.nameState
        }

        viewModelScope.launch {
            getPlaylistById(playlistId)?.name?.let { onNameChange(it) }
        }

        viewModelScope.launch {
            nameState.map { it.isValid }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = false,
            ).collectLatest { setIsConfirmStateEnabled(it) }
        }
    }

    private fun onNameChange(name: String) = _uiState.update {
        it.copy(nameState = it.nameState.copy(value = name))
    }

    private fun onConfirmClick() = uiState.value.let {
        viewModelScope.launch {
            updatePlaylistById(
                id = playlistId,
                name = it.nameState.value,
            )

            navigateBackFromEditPlaylist(playlistId)
        }
    }

    private fun setIsConfirmStateEnabled(isEnabled: Boolean) = _uiState.update {
        it.copy(confirmState = it.confirmState.copy(isEnabled = isEnabled))
    }
}