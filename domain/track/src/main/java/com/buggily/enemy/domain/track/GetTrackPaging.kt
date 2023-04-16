package com.buggily.enemy.domain.track

import androidx.paging.PagingData
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackRepositable
import kotlinx.coroutines.flow.Flow

class GetTrackPaging(
    private val repository: TrackRepositable,
) {

    operator fun invoke(
        search: String,
    ): Flow<PagingData<Track>> = repository.getPaging(
        search = search,
    )
}
