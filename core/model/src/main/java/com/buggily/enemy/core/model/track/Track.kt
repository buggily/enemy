package com.buggily.enemy.core.model.track

import com.buggily.enemy.core.model.Duration
import com.buggily.enemy.core.model.album.Albumable

data class Track(
    override val id: Long,
    override val name: String,
    val duration: Duration,
    val position: Position,
    val artist: Artist,
    val album: Album,
) : Trackable {

    data class Position(
        val track: Int,
        val disc: Int,
    )

    data class Artist(
        val id: Long,
        val name: String,
    )

    data class Album(
        override val id: Long,
        override val name: String,
        val artist: Artist,
    ) : Albumable {

        data class Artist(
            val name: String,
        )
    }
}
