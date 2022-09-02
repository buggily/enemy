package com.buggily.enemy.ui.albums

import com.buggily.enemy.domain.album.Album
import com.buggily.enemy.domain.search.Search

data class AlbumsState(
    val searchState: SearchState,
) {

    data class AlbumState(
        val onAlbumClick: (Album) -> Unit,
    )

    data class SearchState(
        val search: Search,
    ) {

        companion object {
            val default: SearchState
                get() = SearchState(
                    search = Search.default,
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