package com.buggily.enemy.domain.track

data class TrackWithMetadataUi(
    val track: TrackUi,
    val plays: Int,
) {

    val playsText: String
        get() = plays.toString()
}
