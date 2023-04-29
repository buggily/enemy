package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackRepositable

class InsertTrackByPlaylistId(
    private val trackRepository: TrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
        track: Track,
    ) = trackRepository.insertByPlaylistId(
        playlistId = playlistId,
        track = track,
    )
}
