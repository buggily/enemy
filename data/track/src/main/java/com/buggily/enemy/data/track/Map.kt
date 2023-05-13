package com.buggily.enemy.data.track

import com.buggily.core.domain.GetDurationWithMetadata
import com.buggily.enemy.external.track.ExternalTrack
import com.buggily.enemy.local.track.LocalTrack

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

fun ExternalTrack.toWithIndex(
    index: Int,
    getDurationWithMetadata: GetDurationWithMetadata,
): TrackWithIndex = Track(
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
).let {
    TrackWithIndex(
        index = index,
        track = it,
    )
}

fun Track.toLocal(
    playlistId: Long,
    index: Int,
): LocalTrack = LocalTrack(
    id = id,
    playlistId = playlistId,
    index = index,
)

fun TrackWithIndex.toLocal(
    playlistId: Long,
): LocalTrack = LocalTrack(
    id = track.id,
    playlistId = playlistId,
    index = index
)
