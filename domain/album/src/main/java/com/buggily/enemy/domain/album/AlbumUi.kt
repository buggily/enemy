package com.buggily.enemy.domain.album

import com.buggily.enemy.core.data.Artable

data class AlbumUi(
    val id: Long,
    val name: String,
    val artist: Artist,
) : Artable {

    val nameText: String
        get() = name

    override val artUriId: Long
        get() = id

    override val contentDescription: String
        get() = name

    data class Artist(
        val id: Long,
        val name: String,
    ) {

        val nameText: String
            get() = name
    }
}
