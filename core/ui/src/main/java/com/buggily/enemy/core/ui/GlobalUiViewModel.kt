package com.buggily.enemy.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination
import com.buggily.enemy.domain.navigation.NavigateToController
import com.buggily.enemy.domain.navigation.NavigateToCreatePlaylist
import com.buggily.enemy.domain.navigation.NavigateToPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GlobalUiViewModel @Inject constructor(
    private val navigateToCreatePlaylist: NavigateToCreatePlaylist,
    private val navigateToPreferences: NavigateToPreferences,
    private val navigateToController: NavigateToController,
) : ViewModel() {

    private val _uiState: MutableStateFlow<GlobalUiState>
    val uiState: StateFlow<GlobalUiState> get() = _uiState

    val search: StateFlow<String>

    init {
        GlobalUiState(
            searchState = GlobalUiState.SearchState(
                value = String(),
                isVisible = false,
                isSearchable = false,

                onClick = ::onSearchClick,
                onChange = ::onSearchChange,
                onClear = ::onSearchClear,
            ),
            createPlaylistState = GlobalUiState.CreatePlaylistState(
                to = ::toCreatePlaylist,
            ),
            preferencesState = GlobalUiState.PreferencesState(
                to = ::toPreferences,
            ),
            controllerState = GlobalUiState.ControllerState(
                to = ::toController,
            ),
            destinationState = GlobalUiState.DestinationState(
                destination = null,
            ),
        ).let { _uiState = MutableStateFlow(it) }

        val searchState: Flow<GlobalUiState.SearchState> = uiState.map {
            it.searchState
        }

        search = searchState.map { it.value }.debounce(1000).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = String(),
        )
    }

    fun onDestinationChange(destination: NavDestination) = _uiState.update {
        it.copy(destinationState = it.destinationState.copy(destination = destination))
    }

    fun onIsSearchableChange(isSearchable: Boolean) = _uiState.update {
        it.copy(searchState = it.searchState.copy(isSearchable = isSearchable))
    }

    private fun onSearchClick() = _uiState.update {
        val searchState: GlobalUiState.SearchState = it.searchState.copy(
            isVisible = !it.searchState.isVisible,
        )

        it.copy(searchState = searchState)
    }

    private fun onSearchChange(value: String) = _uiState.update {
        val searchState: GlobalUiState.SearchState = it.searchState.copy(
            value = value,
        )

        it.copy(searchState = searchState)
    }

    private fun onSearchClear() = _uiState.update {
        val searchState: GlobalUiState.SearchState = it.searchState.copy(
            value = String(),
            isVisible = false,
        )

        it.copy(searchState = searchState)
    }

    private fun toCreatePlaylist() = navigateToCreatePlaylist(
        name = uiState.value.searchState.value,
    )

    private fun toPreferences() = navigateToPreferences()
    private fun toController() = navigateToController()
}
