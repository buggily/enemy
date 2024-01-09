package com.buggily.enemy.data.track

import com.buggily.enemy.core.data.Albumable
import com.buggily.enemy.core.data.Artistable
import com.buggily.enemy.core.data.DurationWithMetadata
import com.buggily.enemy.core.data.Positionable
import com.buggily.enemy.core.data.Trackable

data class Track(
    override val id: Long,
    override val name: String,

    override val artist: Artist,
    override val album: Album,

    override val position: Position,
    override val duration: DurationWithMetadata,
) : Trackable {

    data class Position(
        override val track: Int,
        override val disc: Int,
    ): Positionable

    data class Artist(
        override val id: Long,
        override val name: String,
    ): Artistable

    data class Album(
        override val id: Long,
        override val name: String,
        override val artist: Artist,
    ) : Albumable {

        data class Artist(
            override val id: Long,
            override val name: String,
        ): Artistable

        override val artUriId: Long
            get() = id

        override val contentDescription: String
            get() = name
    }

    override val uriId: Long
        get() = id
}
