package com.buggily.enemy.data.track.playlist

import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.data.track.to
import com.buggily.enemy.external.track.ExternalTrack
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrack

fun ExternalTrack.toWithIndex(
    index: Int,
): TrackWithIndex = TrackWithIndex(
    track = to(),
    index = index,
)

fun ExternalTrack?.toWithIndex(
    id: Long,
    index: Int,
): TrackWithIndex = when (this) {
    is ExternalTrack -> toWithIndex(
        index = index,
    )

    else -> TrackWithIndex(
        track = Track.getDefault(id),
        index = TrackWithIndex.EMPTY_INDEX,
    )
}

fun ExternalTrack.toLocal(
    playlistId: Long,
    index: Int,
): LocalPlaylistTrack = LocalPlaylistTrack(
    id = id,
    playlistId = playlistId,
    index = index,
)