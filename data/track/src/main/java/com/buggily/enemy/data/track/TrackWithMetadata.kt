package com.buggily.enemy.data.track

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class TrackWithMetadata(
    val track: Track,
    val plays: Int,
    val firstPlayInstant: Instant,
    val lastPlayInstant: Instant,
) {

    companion object {
        const val EMPTY_PLAYS = 0
    }
}
