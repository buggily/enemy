package com.buggily.enemy.data.track

import kotlinx.datetime.Instant

data class TrackWithMetadata(
    val track: Track,
    val plays: Int,
    val firstPlayInstant: Instant,
    val lastPlayInstant: Instant,
) {

    val playsText: String
        get() = plays.toString()
}
