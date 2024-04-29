package com.buggily.enemy.albums

import com.buggily.enemy.domain.album.AlbumUi

data class AlbumsUiState(
    val albumState: AlbumState,
    val searchState: SearchState,
) {

    data class AlbumState(
        val onClick: (AlbumUi) -> Unit,
    )

    data class SearchState(
        val value: String,
    )
}
