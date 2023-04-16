package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackRepositable

class GetTracksByPlaylistId(
    private val repository: TrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
    ): List<Track> = repository.getByPlaylistId(
        playlistId = playlistId,
    )
}
