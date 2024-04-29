package com.buggily.enemy.data.track

data class Track(
    val id: Long,
    val name: String,

    val artist: Artist,
    val album: Album,

    val position: Position,
    val duration: Long,
) {

    val nameText: String
        get() = name

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
    )
}
