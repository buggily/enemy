package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackRepositable

class GetTracksByAlbumId(
    private val repository: TrackRepositable,
) {

    suspend operator fun invoke(
        albumId: Long,
    ): List<Track> =  repository.getByAlbumId(
        albumId = albumId,
    )
}
