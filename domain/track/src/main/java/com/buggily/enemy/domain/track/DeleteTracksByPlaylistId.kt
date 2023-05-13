package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.TrackRepositable

class DeleteTracksByPlaylistId(
    private val trackRepository: TrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
    ) = trackRepository.deleteByPlaylistId(
        playlistId = playlistId,
    )
}
