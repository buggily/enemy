package com.buggily.enemy.data.playlist

import com.buggily.enemy.core.data.InstantWithMetadata

data class Playlist(
    val id: Long = defaultId,
    val name: String,
    val creationInstant: InstantWithMetadata,
    val modificationInstant: InstantWithMetadata,
) {

    private companion object {
        private const val defaultId  = 0L
    }
}
