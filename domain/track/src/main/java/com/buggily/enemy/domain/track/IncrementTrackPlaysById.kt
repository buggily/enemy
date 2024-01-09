package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.TrackRepositable

class IncrementTrackPlaysById(
    private val repository: TrackRepositable,
) {

    suspend operator fun invoke(
        id: Long,
    ) = repository.incrementPlaysById(
        id = id,
    )
}
