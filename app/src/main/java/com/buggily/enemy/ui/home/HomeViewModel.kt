package com.buggily.enemy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buggily.enemy.domain.search.Search
import com.buggily.enemy.domain.use.search.GetSearch
import com.buggily.enemy.domain.use.search.SetSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSearch: GetSearch,
    private val setSearch: SetSearch,
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState>
    val state: StateFlow<HomeState> get() = _state

    init {
        HomeState.default.copy(
            searchState = HomeState.SearchState.default.copy(
                onSearchChange = ::onSearchChange,
                onSearchClearClick = ::onSearchClearClick,
            ),
        ).let { _state = MutableStateFlow(it) }

        viewModelScope.launch {
            getSearch().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = HomeState.SearchState.default.search,
            ).collectLatest { onIsSearchChange(it.isVisible) }

            setSearch(HomeState.SearchState.default.search)
        }
    }

    private fun onSearchChange(searchValue: String) = state.value.let {
        val search: Search = it.searchState.search.copy(
            value = searchValue,
        )

        viewModelScope.launch {
            onSearchChange(search)
            setSearch(search)
        }
    }

    private fun onIsSearchChange(isSearch: Boolean) = state.value.let {
        val search: Search = it.searchState.search.copy(
            isVisible = isSearch,
        )

        onSearchChange(search)
    }

    private fun onSearchClearClick() = onSearchChange(
        searchValue = HomeState.SearchState.default.search.value,
    )

    private fun onSearchChange(search: Search) = setSearchOfSearchState(
        search = search,
    )

    private fun setSearchOfSearchState(search: Search) = state.value.let {
        val searchState: HomeState.SearchState = it.searchState.copy(
            search = search,
        )

        setSearchState(searchState)
    }

    private fun setSearchState(searchState: HomeState.SearchState) = _state.update {
        it.copy(searchState = searchState)
    }
}
