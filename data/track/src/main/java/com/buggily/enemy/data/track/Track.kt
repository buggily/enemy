package com.buggily.enemy.data.track

import com.buggily.enemy.core.data.Albumable
import com.buggily.enemy.core.data.DurationWithMetadata
import com.buggily.enemy.core.data.Trackable

data class Track(
    val id: Long,
    val name: String,
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
        val id: Long,
        val name: String,
        val artist: Artist,
    ) : Albumable {

        data class Artist(
            val name: String,
        )

        override val artUriId: Long
            get() = id

        override val contentDescription: String
            get() = name
    }

    override val uriId: Long
        get() = id
}
