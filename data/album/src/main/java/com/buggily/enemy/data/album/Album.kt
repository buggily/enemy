package com.buggily.enemy.data.album

import com.buggily.enemy.core.data.Albumable

data class Album(
    val id: Long,
    val name: String,
    val artist: Artist,
) : Albumable {

    data class Artist(
        val id: Long,
        val name: String,
    )

    override val artUriId: Long
        get() = id

    override val contentDescription: String
        get() = name
}
