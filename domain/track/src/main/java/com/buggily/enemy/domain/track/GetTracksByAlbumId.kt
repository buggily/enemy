package com.buggily.enemy.domain.track

import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.data.track.TrackRepositable

class GetTracksByAlbumId(
    private val trackRepository: TrackRepositable,
    private val getDurationText: GetDurationText,
    private val getDuration: GetDuration,
) {

    suspend operator fun invoke(
        albumId: Long,
    ): List<TrackUi> = trackRepository.getByAlbumId(albumId).map {
        it.toUi(
            getDurationText = getDurationText,
            getDuration = getDuration,
        )
    }
}
