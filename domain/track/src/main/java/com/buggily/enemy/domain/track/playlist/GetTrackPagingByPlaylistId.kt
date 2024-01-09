package com.buggily.enemy.domain.track.playlist

import androidx.paging.PagingData
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable
import kotlinx.coroutines.flow.Flow

class GetTrackPagingByPlaylistId(
    private val playlistTrackRepository: PlaylistTrackRepositable,
) {

    operator fun invoke(
        playlistId: Long,
    ): Flow<PagingData<TrackWithIndex>> = playlistTrackRepository.getPagingByPlaylistId(
        playlistId = playlistId,
    )
}
