package com.buggily.enemy.domain.track

import androidx.paging.PagingData
import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.data.track.TrackWithMetadata
import kotlinx.coroutines.flow.Flow

class GetTrackPagingByPopularity(
    private val trackRepository: TrackRepositable,
) {

    operator fun invoke(): Flow<PagingData<TrackWithMetadata>> =
        trackRepository.getPagingByPopularity()
}
