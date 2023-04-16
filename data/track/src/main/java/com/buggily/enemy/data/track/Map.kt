package com.buggily.enemy.data.track

import com.buggily.core.domain.GetDurationWithMetadata
import com.buggily.enemy.external.track.ExternalTrack

fun ExternalTrack.to(getDurationWithMetadata: GetDurationWithMetadata): Track = Track(
    id = id,
    name = name,
    duration = getDurationWithMetadata(duration),
    position = Track.Position(
        track = position.track,
        disc = position.disc,
    ),
    artist = Track.Artist(
        id = artist.id,
        name = artist.name,
    ),
    album = Track.Album(
        id = album.id,
        name = album.name,
        artist = Track.Album.Artist(
            name = album.artist.name,
        ),
    ),
)
