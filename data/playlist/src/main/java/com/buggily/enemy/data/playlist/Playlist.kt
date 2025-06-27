package com.buggily.enemy.data.playlist

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
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
