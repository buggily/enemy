package com.buggily.enemy.data.track.ext

import com.buggily.core.domain.GetUiDuration
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.external.track.Track as ExternalTrack

fun ExternalTrack.map(getUiDuration: GetUiDuration): Track = Track(
    id = id,
    name = name,
    duration = getUiDuration(duration),
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
