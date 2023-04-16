package com.buggily.enemy.data.album

import com.buggily.enemy.core.data.Albumable

data class Album(
    override val id: Long,
    override val name: String,
    val artist: Artist,
) : Albumable {

    data class Artist(
        val id: Long,
        val name: String,
    )
}
