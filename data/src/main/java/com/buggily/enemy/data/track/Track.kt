package com.buggily.enemy.data.track

data class Track(
    val id: Long?,
    val name: String?,
    val duration: Long?,
    val position: Position,
    val artist: Artist,
    val album: Album,
) {

    data class Position(
        val track: Int?,
        val disc: Int?,
    )

    data class Artist(
        val id: Long?,
        val name: String?,
    )

    data class Album(
        val id: Long?,
        val name: String?,
        val artist: Artist,
    ) {

        data class Artist(
            val name: String?
        )
    }
}
