package com.buggily.enemy.domain.map.track

import com.buggily.enemy.domain.map.QueryMapper
import com.buggily.enemy.domain.use.GetDuration
import com.buggily.enemy.data.Track as Input
import com.buggily.enemy.domain.track.Track as Output

class TrackMapper(
    private val getDuration: GetDuration,
) : QueryMapper<Input, Output> {

    override fun mapTo(input: Input): Output? = with(input) {
        val id: Long = id ?: return@with null
        val name: String = name ?: return@with null
        val duration: Output.Duration = duration?.let { getDuration(it) } ?: return@with null

        val trackPosition: Int = position.track ?: return@with null
        val discPosition: Int = position.disc ?: Output.Position.DISC_DEFAULT

        val position = Output.Position(
            track = trackPosition,
            disc = discPosition,
        )

        val artistId: Long = artist.id ?: return@with null
        val artistName: String = artist.name ?: return@with null

        val artist = Output.Artist(
            id = artistId,
            name = artistName,
        )

        val albumId: Long = album.id ?: return@with null
        val albumName: String = album.name ?: return@with null

        val albumArtistName: String = album.artist.name ?: return@with null

        val albumArtist = Output.Album.Artist(
            name = albumArtistName,
        )

        val album = Output.Album(
            id = albumId,
            name = albumName,
            artist = albumArtist,
        )

        return Output(
            id = id,
            name = name,
            duration = duration,
            position = position,
            artist = artist,
            album = album,
        )
    }
}
