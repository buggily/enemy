package com.buggily.enemy.data.playlist

import kotlinx.datetime.Instant

data class Playlist(
    val id: Long = DEFAULT_ID,
    val name: String,
    val creationInstant: Instant,
    val modificationInstant: Instant,
) {

    private companion object {
        private const val DEFAULT_ID = 0L
    }
}
