package com.buggily.enemy.data.track

import kotlinx.datetime.Instant

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
