package com.buggily.enemy.albums

import com.buggily.enemy.data.album.Album

data class AlbumsUiState(
    val albumState: AlbumState,
    val searchState: SearchState,
) {

    data class AlbumState(
        val onClick: (Album) -> Unit,
    )

    data class SearchState(
        val value: String,
    )
}
