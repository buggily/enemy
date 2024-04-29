package com.buggily.enemy.domain.track

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.data.track.TrackWithMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTrackPagingByPopularity(
    private val trackRepository: TrackRepositable,
    private val getDurationText: GetDurationText,
    private val getDuration: GetDuration,
) {

    operator fun invoke(): Flow<PagingData<TrackWithMetadataUi>> =
        trackRepository.getPagingByPopularity().map {
            it.map { track: TrackWithMetadata ->
                track.toUi(
                    getDurationText = getDurationText,
                    getDuration = getDuration
                )
            }
        }
}
