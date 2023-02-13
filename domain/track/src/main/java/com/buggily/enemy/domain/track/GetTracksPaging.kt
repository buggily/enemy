package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.repository.TrackRepositable

class GetTracksPaging(
    private val repository: TrackRepositable,
) {

    operator fun invoke(
        search: String,
    ) = repository.getPaging(
        search = search,
    )
}
