package com.buggily.enemy.domain.playlistWithTracks

import com.buggily.enemy.data.playlistWithTracks.PlaylistWithTracksRepositable

class InsertTrackByIdAndPlaylistId(
    private val playlistWithTracksRepository: PlaylistWithTracksRepositable,
) {

    suspend operator fun invoke(
        trackId: Long,
        playlistId: Long,
    ) = playlistWithTracksRepository.insertTrackByIdAndPlaylistId(
        trackId = trackId,
        playlistId = playlistId,
    )
}
