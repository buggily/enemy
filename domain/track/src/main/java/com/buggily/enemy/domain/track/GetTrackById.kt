package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackRepositable

class GetTrackById(
    private val trackRepository: TrackRepositable,
) {

    suspend operator fun invoke(
        id: Long,
    ): Track? = trackRepository.getById(
        id = id,
    )
}
