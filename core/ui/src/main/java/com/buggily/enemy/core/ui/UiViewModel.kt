package com.buggily.enemy.core.ui

import androidx.lifecycle.ViewModel
import com.buggily.core.domain.GetTimeOfDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UiViewModel @Inject constructor(
    getTimeOfDay: GetTimeOfDay,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState>
    val state: StateFlow<UiState> get() = _state

    val search: Flow<String>

    init {
        UiState.default.copy(
            greetingState = UiState.GreetingState.default.copy(
                timeOfDay = getTimeOfDay(),
                onVisible = ::onGreetingVisible,
            ),
            searchState = UiState.SearchState.default.copy(
                onClick = ::onSearchClick,
                onChange = ::onSearchChange,
                onClear = ::onSearchClear,
            ),
            fabState = UiState.FabState.default.copy(
                onClick = ::onFabClick,
            ),
        ).let { _state = MutableStateFlow(it) }

        val searchState: Flow<UiState.SearchState> = state.map {
            it.searchState
        }

        search = searchState.map {
            it.value
        }
            .distinctUntilChanged()
            .debounce(1000)
    }

    private fun onGreetingVisible() = _state.update {
        val greetingState: UiState.GreetingState = it.greetingState.copy(
            isVisible = !it.greetingState.isVisible,
        )

        it.copy(greetingState = greetingState)
    }

    private fun onSearchClick() = _state.update {
        val searchState: UiState.SearchState = it.searchState.copy(
            isVisible = !it.searchState.isVisible,
        )

        it.copy(searchState = searchState)
    }

    private fun onSearchChange(value: String) = _state.update {
        val searchState: UiState.SearchState = it.searchState.copy(
            value = value,
        )

        it.copy(searchState = searchState)
    }

    private fun onSearchClear() = _state.update {
        val searchState: UiState.SearchState = it.searchState.copy(
            value = UiState.SearchState.default.value,
            isVisible = !it.searchState.isVisible,
        )

        it.copy(searchState = searchState)
    }

    private fun onFabClick() = _state.update {
        val fabEventState = UiState.FabEventState.Event.Click(
            onEvent = ::resetFabState,
        )

        it.copy(fabEventState = fabEventState)
    }

    private fun resetFabState() = _state.update {
        it.copy(fabEventState = UiState.FabEventState.Default)
    }
}
