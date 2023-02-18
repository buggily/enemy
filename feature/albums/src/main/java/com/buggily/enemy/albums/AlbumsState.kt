package com.buggily.enemy.albums

import com.buggily.enemy.core.model.album.Album

data class AlbumsState(
    val searchState: SearchState,
) {

    data class SearchState(
        val value: String,
    ) {

        companion object {
            val default: SearchState
                get() = SearchState(
                    value = String(),
                )
        }
    }

    data class AlbumState(
        val onClick: (Album) -> Unit,
    )

    companion object {
        val default: AlbumsState
            get() = AlbumsState(
                searchState = SearchState.default,
            )
    }
}
