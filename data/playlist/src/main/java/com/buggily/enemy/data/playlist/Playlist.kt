package com.buggily.enemy.data.playlist

import com.buggily.enemy.core.data.InstantWithMetadata
import com.buggily.enemy.core.data.Playlistable

data class Playlist(
    override val id: Long = DEFAULT_ID,
    override val name: String,
    override val creationInstant: InstantWithMetadata,
    override val modificationInstant: InstantWithMetadata,
): Playlistable {

    private companion object {
        private const val DEFAULT_ID  = 0L
    }
}
