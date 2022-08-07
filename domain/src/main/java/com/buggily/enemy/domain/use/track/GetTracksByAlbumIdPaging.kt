package com.buggily.enemy.domain.use.track

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.data.repository.track.TrackRepositable
import com.buggily.enemy.domain.map.track.TrackMapper
import com.buggily.enemy.domain.track.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTracksByAlbumIdPaging(
    private val repository: TrackRepositable,
    private val mapper: TrackMapper,
) {

    operator fun invoke(
        albumId: Long?,
    ): Flow<PagingData<Result<Track>>> = repository.getPagingByAlbumId(albumId).map { paging ->
        paging.map { mapper.mapToResult(it) }
    }
}
