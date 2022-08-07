package com.buggily.enemy.ui.albums

data class AlbumsState(
    val searchState: SearchState,
) {

    data class SearchState(
        val search: String,
        val onSearchChange: (String) -> Unit,
    ) {

        companion object {
            val default: SearchState
                get() = SearchState(
                    search = String(),
                    onSearchChange = {},
                )
        }
    }

    companion object {
        val default: AlbumsState
            get() = AlbumsState(
                searchState = SearchState.default,
            )
    }
}