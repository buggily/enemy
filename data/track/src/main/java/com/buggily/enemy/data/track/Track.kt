package com.buggily.enemy.data.track

import kotlin.time.Duration

data class Track(
    val id: Long,
    val name: String,

    val artist: Artist,
    val album: Album,

    val position: Position,
    val duration: Long,
) {

    data class Artist(
        val id: Long,
        val name: String,
    )

    data class Album(
        val id: Long,
        val name: String,
        val artist: Artist,
    ) {

        data class Artist(
            val id: Long,
            val name: String,
        )
    }

    data class Position(
        val track: Int,
        val disc: Int,
    ) {

        companion object {
            const val EMPTY_TRACK = -1
            const val EMPTY_DISC = -1
        }
    }

    companion object {
        private const val EMPTY_ID = -1L

        fun getDefault(id: Long): Track = Track(
            id = id,
            name = String(),

            artist = Artist(
                id = EMPTY_ID,
                name = String(),
            ),

            album = Album(
                id = EMPTY_ID,
                name = String(),

                artist = Album.Artist(
                    id = EMPTY_ID,
                    name = String(),
                ),
            ),

            position = Position(
                track = Position.EMPTY_TRACK,
                disc = Position.EMPTY_DISC,
            ),

            duration = Duration.ZERO.inWholeMilliseconds,
        )
    }
}
