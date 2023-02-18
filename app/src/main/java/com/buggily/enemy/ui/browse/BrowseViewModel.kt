package com.buggily.enemy.ui.browse

import androidx.lifecycle.ViewModel
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
class BrowseViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableStateFlow<BrowseState>
    val state: StateFlow<BrowseState> get() = _state

    val search: Flow<String>

    init {
        BrowseState.default.copy(
            searchState = BrowseState.SearchState.default.copy(
                onChange = ::onSearchChange,
                onClear = ::onSearchClear,
                onToggleVisibility = ::onToggleSearchVisibility,
            ),
            tabState = BrowseState.TabState.default.copy(
                onClick = ::onTabClick,
            ),
        ).let { _state = MutableStateFlow(it) }

        val searchState: Flow<BrowseState.SearchState> = state.map {
            it.searchState
        }

        search = searchState.map {
            it.value
        }
            .debounce(1000)
            .distinctUntilChanged()
    }

    private fun onSearchChange(value: String) = state.value.let {
        val searchState: BrowseState.SearchState = it.searchState.copy(
            value = value,
        )

        setSearchState(searchState)
    }

    private fun onSearchClear() = state.value.let {
        val searchState: BrowseState.SearchState = it.searchState.copy(
            value = BrowseState.SearchState.default.value,
        )

        setSearchState(searchState)
    }

    private fun onToggleSearchVisibility() = state.value.let {
        val searchState: BrowseState.SearchState = it.searchState.copy(
            value = BrowseState.SearchState.default.value,
            isVisible = !it.searchState.isVisible,
        )

        setSearchState(searchState)
    }

    private fun onTabClick(
        tab: BrowseState.TabState.Tab,
    ) = state.value.let {
        val tabState: BrowseState.TabState = it.tabState.copy(
            tab = tab,
        )

        setTabState(tabState)
    }

    private fun setSearchState(
        searchState: BrowseState.SearchState,
    ) = _state.update {
        it.copy(searchState = searchState)
    }

    private fun setTabState(
        tabState: BrowseState.TabState,
    ) = _state.update {
        it.copy(tabState = tabState)
    }
}
