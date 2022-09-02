package com.buggily.enemy.ui.home

import com.buggily.enemy.domain.search.Search

data class HomeState(
    val searchState: SearchState,
) {

    data class SettingsState(
        val onSettingsClick: () -> Unit,
    )

    data class SearchState(
        val search: Search,
        val onSearchChange: (String) -> Unit,
        val onSearchClearClick: () -> Unit,
    ) {

        val isSearchClear: Boolean
            get() = search.value.isNotEmpty()

        companion object {
            val default: SearchState
                get() = SearchState(
                    search = Search.default,
                    onSearchChange = {},
                    onSearchClearClick = {},
                )
        }
    }

    companion object {
        val default: HomeState
            get() = HomeState(
                searchState = SearchState.default,
            )
    }
}
