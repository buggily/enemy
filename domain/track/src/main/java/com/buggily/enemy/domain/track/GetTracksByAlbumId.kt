package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackRepositable

class GetTracksByAlbumId(
    private val trackRepository: TrackRepositable,
) {

    suspend operator fun invoke(
        albumId: Long,
    ): List<Track> =  trackRepository.getByAlbumId(
        albumId = albumId,
    )
}
