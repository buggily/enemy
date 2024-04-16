package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.TrackRepositable

class IncrementTrackPlaysById(
    private val trackRepository: TrackRepositable,
) {

    suspend operator fun invoke(
        id: Long,
    ) = trackRepository.incrementPlaysById(
        id = id,
    )
}
