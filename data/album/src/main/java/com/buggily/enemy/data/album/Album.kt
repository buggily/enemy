package com.buggily.enemy.data.album

import com.buggily.enemy.core.data.Albumable
import com.buggily.enemy.core.data.Artistable

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
