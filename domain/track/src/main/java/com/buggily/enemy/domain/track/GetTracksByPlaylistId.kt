package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.data.track.TrackWithIndex

class GetTracksByPlaylistId(
    private val repository: TrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
    ): List<TrackWithIndex> = repository.getByPlaylistId(
        playlistId = playlistId,
    )
}
