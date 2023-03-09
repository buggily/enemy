package com.buggily.enemy.domain.track

import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.data.track.repository.TrackRepositable

class GetTracksByAlbumId(
    private val repository: TrackRepositable,
) {

    suspend operator fun invoke(
        albumId: Long,
    ): List<Track> =  repository.getByAlbumId(
        albumId = albumId,
    )
}
