package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.data.track.TrackWithIndex

class RemoveTrackByPlaylistId(
    private val trackRepository: TrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
        track: TrackWithIndex,
    ) = trackRepository.deleteByPlaylistId(
        playlistId = playlistId,
        track = track,
    )
}
