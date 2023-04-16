package com.buggily.enemy.data.track

import com.buggily.enemy.core.data.Albumable
import com.buggily.enemy.core.data.DurationWithMetadata
import com.buggily.enemy.core.data.Trackable

data class Track(
    override val id: Long,
    override val name: String,
    val duration: DurationWithMetadata,
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
