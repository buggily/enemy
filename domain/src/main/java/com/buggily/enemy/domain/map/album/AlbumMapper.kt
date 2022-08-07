package com.buggily.enemy.domain.map.album

import com.buggily.enemy.domain.map.QueryMapper
import com.buggily.enemy.data.Album as Input
import com.buggily.enemy.domain.album.Album as Output


class AlbumMapper : QueryMapper<Input, Output> {

    override fun mapTo(input: Input): Output? = with(input) {
        val id: Long = input.id ?: return@with null
        val name: String = input.name ?: return@with null

        val artist: Input.Artist = input.artist
        val artistId: Long = artist.id ?: return@with null
        val artistName: String = artist.name ?: return@with null

        return Output(
            id = id,
            name = name,
            artist = Output.Artist(
                id = artistId,
                name = artistName,
            )
        )
    }
}
