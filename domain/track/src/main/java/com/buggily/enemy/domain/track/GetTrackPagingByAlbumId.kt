package com.buggily.enemy.domain.track

import androidx.paging.PagingData
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackRepositable
import kotlinx.coroutines.flow.Flow

class GetTrackPagingByAlbumId(
    private val trackRepository: TrackRepositable,
) {

    operator fun invoke(
        albumId: Long,
    ): Flow<PagingData<Track>> = trackRepository.getPagingByAlbumId(
        albumId = albumId,
    )
}
