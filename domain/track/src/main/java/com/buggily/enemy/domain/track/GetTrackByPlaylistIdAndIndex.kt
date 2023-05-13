package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.data.track.TrackWithIndex

class GetTrackByPlaylistIdAndIndex(
    private val repository: TrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
        index: Int,
    ): TrackWithIndex? = repository.getByPlaylistIdAndIndex(
        playlistId = playlistId,
        index = index,
    )
}
