package com.buggily.enemy.domain.track

import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.data.track.TrackRepositable

class GetTrackById(
    private val trackRepository: TrackRepositable,
    private val getDurationText: GetDurationText,
    private val getDuration: GetDuration,
) {

    suspend operator fun invoke(
        id: Long,
    ): TrackUi? = trackRepository.getById(id)?.toUi(
        getDurationText = getDurationText,
        getDuration = getDuration,
    )
}
