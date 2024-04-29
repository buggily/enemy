package com.buggily.enemy.domain.track.playlist

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable
import com.buggily.enemy.domain.track.TrackWithIndexUi
import com.buggily.enemy.domain.track.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTrackPagingByPlaylistId(
    private val playlistTrackRepository: PlaylistTrackRepositable,
    private val getDuration: GetDuration,
    private val getDurationText: GetDurationText,
) {

    operator fun invoke(
        playlistId: Long,
    ): Flow<PagingData<TrackWithIndexUi>> = playlistTrackRepository.getPagingByPlaylistId(
        playlistId = playlistId,
    ).map {
        it.map { track: TrackWithIndex ->
            track.toUi(
                getDuration = getDuration,
                getDurationText = getDurationText,
            )
        }
    }
}
