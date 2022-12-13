package com.buggily.enemy.domain.track

import com.buggily.enemy.data.track.repository.TrackRepositable

class GetTracksByAlbumId(
    private val repository: TrackRepositable,
) {

    operator fun invoke(albumId: Long) =  repository.getByAlbumId(albumId)
}
