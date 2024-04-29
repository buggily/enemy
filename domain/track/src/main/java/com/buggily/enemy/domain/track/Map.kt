package com.buggily.enemy.domain.track

import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.data.track.TrackWithMetadata

fun TrackUi.to(): Track = Track(
    id = id,
    name = name,

    album = Track.Album(
        id = album.id,
        name = album.name,
        artist = Track.Album.Artist(
            id = album.artist.id,
            name = album.artist.name,
        ),
    ),
    artist = Track.Artist(
        id = artist.id,
        name = artist.name,
    ),
    position = Track.Position(
        disc = position.disc,
        track = position.track,
    ),
    duration = duration.duration.inWholeMilliseconds,
)

fun Track.toUi(
    getDurationText: GetDurationText,
    getDuration: GetDuration,
): TrackUi = TrackUi(
    id = id,
    name = name,

    album = TrackUi.Album(
        id = album.id,
        name = album.name,
        artist = TrackUi.Album.Artist(
            id = album.artist.id,
            name = album.artist.name,
        )
    ),
    artist = TrackUi.Artist(
        id = artist.id,
        name = artist.name,
    ),
    position = TrackUi.Position(
        disc = position.disc,
        track = position.track,
    ),
    duration = TrackUi.Duration(
        duration = getDuration(duration),
        text = getDurationText(duration),
    )
)

fun TrackWithMetadata.toUi(
    getDurationText: GetDurationText,
    getDuration: GetDuration,
): TrackWithMetadataUi = TrackWithMetadataUi(
    plays = plays,
    track = track.toUi(
        getDurationText = getDurationText,
        getDuration = getDuration,
    ),
)

fun TrackWithIndex.toUi(
    getDuration: GetDuration,
    getDurationText: GetDurationText,
): TrackWithIndexUi = TrackWithIndexUi(
    index = index,
    track = track.toUi(
        getDurationText = getDurationText,
        getDuration = getDuration,
    ),
)
