package com.buggily.enemy.domain.track.playlist

import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable

class GetTrackByPlaylistIdAndIndex(
    private val playlistTrackRepository: PlaylistTrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
        index: Int,
    ): TrackWithIndex? = playlistTrackRepository.getByPlaylistIdAndIndex(
        playlistId = playlistId,
        index = index,
    )
}
