package com.buggily.enemy.albums

import com.buggily.enemy.core.model.TimeOfDay
import com.buggily.enemy.core.model.album.Album

data class AlbumsState(
    val timeOfDay: TimeOfDay?,
) {

    data class AlbumState(
        val onAlbumClick: (Album) -> Unit,
    )

    data class PreferencesState(
        val onPreferencesClick: () -> Unit,
    )

    companion object {
        val default: AlbumsState
            get() = AlbumsState(
                timeOfDay = null,
            )
    }
}
