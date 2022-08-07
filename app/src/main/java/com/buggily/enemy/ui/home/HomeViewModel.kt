package com.buggily.enemy.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _state: MutableStateFlow<HomeState>
    val state: StateFlow<HomeState> get() = _state

    init {
        HomeState.default.copy(
            searchState = HomeState.SearchState.default.copy(
                onIsSearchChange = ::onIsSearchChange,
                onSearchChange = ::onSearchChange,
                onSearchClearClick = ::onSearchClearClick,
            ),
        ).let { _state = MutableStateFlow(it) }
    }

    private fun onIsSearchChange(isSearch: Boolean) = setIsSearchOfSearchState(
        isSearch = isSearch,
    )

    private fun onSearchChange(search: String) = setSearchOfSearchState(
        search = search,
    )

    private fun onSearchClearClick() = setSearchOfSearchState(
        search = HomeState.SearchState.default.search,
    )

    private fun setIsSearchOfSearchState(isSearch: Boolean) = state.value.let {
        val searchState: HomeState.SearchState = it.searchState.copy(
            isSearch = isSearch,
        )

        setSearchState(searchState)
    }

    private fun setSearchOfSearchState(search: String) = state.value.let {
        val searchState: HomeState.SearchState = it.searchState.copy(
            search = search,
        )

        setSearchState(searchState)
    }

    private fun setSearchState(searchState: HomeState.SearchState) = _state.update {
        it.copy(searchState = searchState)
    }
}
