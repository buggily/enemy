package com.buggily.enemy.domain.track.playlist

import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable

class InsertTrackByPlaylistId(
    private val playlistTrackRepository: PlaylistTrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
        track: Track,
    ) = playlistTrackRepository.insertByPlaylistId(
        playlistId = playlistId,
        track = track,
    )
}
