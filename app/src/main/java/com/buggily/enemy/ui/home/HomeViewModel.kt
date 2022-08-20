package com.buggily.enemy.ui.home

import androidx.lifecycle.ViewModel
import com.buggily.enemy.di.DebounceQualifier
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
class HomeViewModel @Inject constructor(
    @DebounceQualifier debounce: Long,
) : ViewModel() {

    val search: Flow<String>

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

        search = state.map {
            it.searchState.search
        }.debounce(debounce).distinctUntilChanged()
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
