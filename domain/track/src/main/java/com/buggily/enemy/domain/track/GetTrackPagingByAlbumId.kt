package com.buggily.enemy.domain.track

import androidx.paging.PagingData
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.data.track.repository.TrackRepositable
import kotlinx.coroutines.flow.Flow

class GetTrackPagingByAlbumId(
    private val repository: TrackRepositable,
) {

    operator fun invoke(
        albumId: Long,
    ): Flow<PagingData<Track>> = repository.getPagingByAlbumId(
        albumId = albumId,
    )
}
