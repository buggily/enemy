package com.buggily.enemy.domain.track

import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.data.track.repository.TrackRepositable

class GetTracksByPlaylistId(
    private val repository: TrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
    ): List<Track> = repository.getByPlaylistId(
        playlistId = playlistId,
    )
}
