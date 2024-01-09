package com.buggily.enemy.domain.track.playlist

import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable

class GetTracksByPlaylistId(
    private val playlistTrackRepository: PlaylistTrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
    ): List<TrackWithIndex> = playlistTrackRepository.getByPlaylistId(
        playlistId = playlistId,
    )
}
