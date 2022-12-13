package com.buggily.enemy.local.track

data class Track(
    val id: Long,
    val name: String,
    val duration: Long,
    val position: Position,
    val artist: Artist,
    val album: Album,
) {

    data class Position(
        val track: Int,
        val disc: Int,
    ) {

        companion object {
            const val defaultDisc = 1
        }
    }

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
            val name: String,
        )
    }
}
