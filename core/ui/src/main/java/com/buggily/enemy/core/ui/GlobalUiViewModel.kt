package com.buggily.enemy.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buggily.core.domain.GetTimeOfDay
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
    getTimeOfDay: GetTimeOfDay,
) : ViewModel() {

    private val _uiState: MutableStateFlow<GlobalUiState>
    val uiState: StateFlow<GlobalUiState> get() = _uiState

    val search: StateFlow<String>

    init {
        GlobalUiState.default.copy(
            greetingState = GlobalUiState.GreetingState.default.copy(
                timeOfDay = getTimeOfDay(),
                onVisible = ::onGreetingVisible,
            ),
            searchState = GlobalUiState.SearchState.default.copy(
                onClick = ::onSearchClick,
                onChange = ::onSearchChange,
                onClear = ::onSearchClear,
            ),
            createPlaylistState = GlobalUiState.CreatePlaylistState.default.copy(
                to = ::toCreatePlaylist,
            ),
            preferencesState = GlobalUiState.PreferencesState.default.copy(
                to = ::toPreferences,
            ),
            controllerState = GlobalUiState.ControllerState.default.copy(
                to = ::toController,
            ),
        ).let { _uiState = MutableStateFlow(it) }

        val searchState: Flow<GlobalUiState.SearchState> = uiState.map {
            it.searchState
        }

        search = searchState.map { it.value }.debounce(1000).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = GlobalUiState.SearchState.default.value,
        )
    }

    private fun onGreetingVisible() = _uiState.update {
        val greetingState: GlobalUiState.GreetingState = it.greetingState.copy(
            isVisible = !it.greetingState.isVisible,
        )

        it.copy(greetingState = greetingState)
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
            value = GlobalUiState.SearchState.default.value,
            isVisible = !it.searchState.isVisible,
        )

        it.copy(searchState = searchState)
    }

    private fun toCreatePlaylist() = uiState.value.let {
        navigateToCreatePlaylist(name = it.searchState.value)
    }

    private fun toPreferences() = navigateToPreferences()
    private fun toController() = navigateToController()
}
