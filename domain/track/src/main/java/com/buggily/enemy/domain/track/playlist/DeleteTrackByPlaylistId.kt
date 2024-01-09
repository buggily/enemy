package com.buggily.enemy.domain.track.playlist

import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable

class DeleteTrackByPlaylistId(
    private val playlistTrackRepository: PlaylistTrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
        track: TrackWithIndex,
    ) = playlistTrackRepository.deleteByPlaylistId(
        playlistId = playlistId,
        track = track,
    )
}
