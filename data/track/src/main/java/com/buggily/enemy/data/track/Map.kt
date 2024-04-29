package com.buggily.enemy.data.track

import com.buggily.enemy.external.track.ExternalTrack
import kotlinx.datetime.Instant

fun ExternalTrack.to(): Track = Track(
    id = id,
    name = name,

    artist = Track.Artist(
        id = artist.id,
        name = artist.name,
    ),

    album = Track.Album(
        id = album.id,
        name = album.name,
        artist = Track.Album.Artist(
            id = album.artist.id,
            name = album.artist.name,
        ),
    ),

    position = Track.Position(
        track = position.track,
        disc = position.disc,
    ),

    duration = duration,
)

fun ExternalTrack.toWithMetadata(
    plays: Int,
    firstPlayInstant: Instant,
    lastPlayInstant: Instant,
): TrackWithMetadata = TrackWithMetadata(
    track = to(),
    plays = plays,
    firstPlayInstant = firstPlayInstant,
    lastPlayInstant = lastPlayInstant,
)
