package com.buggily.enemy.data.track.playlist

import com.buggily.enemy.core.domain.GetDurationWithMetadata
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.data.track.to
import com.buggily.enemy.external.track.ExternalTrack
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrack

fun ExternalTrack.toWithIndex(
    index: Int,
    getDurationWithMetadata: GetDurationWithMetadata,
): TrackWithIndex = TrackWithIndex(
    track = to(getDurationWithMetadata),
    index = index,
)

fun Track.toLocal(
    playlistId: Long,
    index: Int,
): LocalPlaylistTrack = LocalPlaylistTrack(
    id = id,
    playlistId = playlistId,
    index = index,
)

fun TrackWithIndex.toLocal(
    playlistId: Long,
): LocalPlaylistTrack = LocalPlaylistTrack(
    id = track.id,
    playlistId = playlistId,
    index = index,
)
