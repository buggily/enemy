package com.buggily.enemy.feature.playlist.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.core.data.InstantWithMetadata
import com.buggily.enemy.core.domain.GetInstantWithMetadata
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.domain.navigation.NavigateBackFromCreatePlaylist
import com.buggily.enemy.domain.playlist.InsertPlaylist
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
class CreatePlaylistViewModel @Inject constructor(
    private val insertPlaylist: InsertPlaylist,
    private val getInstantWithMetadata: GetInstantWithMetadata,
    private val navigateBackFromCreatePlaylist: NavigateBackFromCreatePlaylist,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val playlistName: String

    private val _uiState: MutableStateFlow<CreatePlaylistUiState>
    val uiState: StateFlow<CreatePlaylistUiState> get() = _uiState

    init {
        val playlistNameKey: String = NavigationDestination.Playlist.Create.NAME
        playlistName = checkNotNull(savedStateHandle[playlistNameKey])

        CreatePlaylistUiState(
            nameState = CreatePlaylistUiState.NameState(
                value = playlistName,
                onChange = ::onNameChange,
            ),
            confirmState = CreatePlaylistUiState.ConfirmState(
                isEnabled = false,
                onClick = ::onConfirmClick,
            ),
        ).let { _uiState = MutableStateFlow(it) }

        val nameState: Flow<CreatePlaylistUiState.NameState> = uiState.map {
            it.nameState
        }

        val isConfirmEnabled: Flow<Boolean> = nameState.map { it.value }.map {
            it.isNotBlank()
        }

        viewModelScope.launch {
            isConfirmEnabled.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = false,
            ).collectLatest { setIsConfirmStateEnabled(it) }
        }
    }

    private fun onNameChange(value: String) = _uiState.update {
        it.copy(nameState = it.nameState.copy(value = value))
    }

    private fun onConfirmClick() = uiState.value.let {
        val name: String = it.nameState.value
        val instant: InstantWithMetadata = getInstantWithMetadata()

        val playlist = Playlist(
            name = name,
            creationInstant = instant,
            modificationInstant = instant,
        )

        viewModelScope.launch {
            insertPlaylist(playlist)
            navigateBackFromCreatePlaylist(playlistName)
        }
    }

    private fun setIsConfirmStateEnabled(isEnabled: Boolean) = _uiState.update {
        it.copy(confirmState = it.confirmState.copy(isEnabled = isEnabled))
    }
}
