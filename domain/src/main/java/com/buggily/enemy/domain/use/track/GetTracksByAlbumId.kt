package com.buggily.enemy.domain.use.track

import com.buggily.enemy.data.repository.track.TrackRepositable
import com.buggily.enemy.domain.map.track.TrackMapper
import com.buggily.enemy.domain.track.Track

class GetTracksByAlbumId(
    private val repository: TrackRepositable,
    private val mapper: TrackMapper,
) {

    operator fun invoke(
        albumId: Long?,
    ): List<Track> =  repository.getByAlbumId(albumId).mapNotNull {
        mapper.mapTo(it)
    }
}
