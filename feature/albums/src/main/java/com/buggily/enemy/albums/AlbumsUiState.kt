package com.buggily.enemy.albums

import com.buggily.enemy.data.album.Album

data class AlbumsUiState(
    val albumState: AlbumState,
    val searchState: SearchState,
) {

    data class AlbumState(
        val onClick: (Album) -> Unit,
    ) {

        companion object {
            val default: AlbumState
                get() = AlbumState(
                    onClick = {},
                )
        }
    }

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

    companion object {
        val default: AlbumsUiState
            get() = AlbumsUiState(
                albumState = AlbumState.default,
                searchState = SearchState.default,
            )
    }
}
