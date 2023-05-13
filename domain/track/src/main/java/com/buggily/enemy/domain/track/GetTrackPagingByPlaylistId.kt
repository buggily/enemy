package com.buggily.enemy.domain.track

import androidx.paging.PagingData
import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.data.track.TrackWithIndex
import kotlinx.coroutines.flow.Flow

class GetTrackPagingByPlaylistId(
    private val repository: TrackRepositable,
) {

    operator fun invoke(
        playlistId: Long,
    ): Flow<PagingData<TrackWithIndex>> = repository.getPagingByPlaylistId(
        playlistId = playlistId,
    )
}
