package com.buggily.enemy.data.track

import com.buggily.enemy.core.domain.GetDurationWithMetadata
import com.buggily.enemy.external.track.ExternalTrack
import kotlinx.datetime.Instant

fun ExternalTrack.to(getDurationWithMetadata: GetDurationWithMetadata): Track = Track(
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

    duration = getDurationWithMetadata(duration),
)

fun ExternalTrack.toWithMetadata(
    plays: Int,
    firstPlayInstant: Instant,
    lastPlayInstant: Instant,
    getDurationWithMetadata: GetDurationWithMetadata,
): TrackWithMetadata = TrackWithMetadata(
        track = to(getDurationWithMetadata),
        plays = plays,
        firstPlayInstant = firstPlayInstant,
        lastPlayInstant = lastPlayInstant,
    )
