package com.buggily.enemy.domain.track

import com.buggily.enemy.domain.album.Albumable

data class Track(
    override val id: Long,
    override val name: String,
    val duration: Duration,
    val position: Position,
    val artist: Artist,
    val album: Album,
) : Trackable {

    data class Duration(
        val identity: Long,
        val milliseconds: Long,
        val seconds: Long,
        val minutes: Long,
        val display: String,
    )

    data class Position(
        val track: Int,
        val disc: Int,
    ) {

        companion object {
            const val DISC_DEFAULT = 1
        }
    }

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