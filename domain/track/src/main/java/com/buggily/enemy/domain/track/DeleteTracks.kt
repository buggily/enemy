package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.TrackRepositable

class DeleteTracks(
    private val trackRepository: TrackRepositable,
) {

    suspend operator fun invoke() = trackRepository.delete()
}
