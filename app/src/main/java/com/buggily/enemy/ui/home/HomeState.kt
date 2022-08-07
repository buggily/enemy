package com.buggily.enemy.ui.home

data class HomeState(
    val searchState: SearchState,
    val settingsState: SettingsState,
) {

    data class SearchState(
        val isSearch: Boolean,
        val search: String,
        val onIsSearchChange: (Boolean) -> Unit,
        val onSearchChange: (String) -> Unit,
        val onSearchClearClick: () -> Unit,
    ) {

        val isSearchClear: Boolean
            get() = search.isNotEmpty()

        companion object {
            val default: SearchState
                get() = SearchState(
                    isSearch = false,
                    search = String(),
                    onIsSearchChange = {},
                    onSearchChange = {},
                    onSearchClearClick = {},
                )
        }
    }

    data class SettingsState(
        val onSettingsClick: () -> Unit,
    ) {

        companion object {
            val default: SettingsState
                get() = SettingsState(
                    onSettingsClick = {},
                )
        }
    }

    companion object {
        val default: HomeState
            get() = HomeState(
                searchState = SearchState.default,
                settingsState = SettingsState.default,
            )
    }
}