package com.buggily.enemy.domain.track

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackRepositable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTrackPaging(
    private val trackRepository: TrackRepositable,
    private val getDurationText: GetDurationText,
    private val getDuration: GetDuration,
) {

    operator fun invoke(
        search: String,
    ): Flow<PagingData<TrackUi>> = trackRepository.getPaging(search).map {
        it.map { track: Track ->
            track.toUi(
                getDurationText = getDurationText,
                getDuration = getDuration,
            )
        }
    }
}
