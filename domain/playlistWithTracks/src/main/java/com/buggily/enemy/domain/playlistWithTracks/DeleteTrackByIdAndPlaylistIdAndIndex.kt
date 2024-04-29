package com.buggily.enemy.domain.playlistWithTracks

import com.buggily.enemy.data.playlistWithTracks.PlaylistWithTracksRepositable

class DeleteTrackByIdAndPlaylistIdAndIndex(
    private val playlistWithTracksRepository: PlaylistWithTracksRepositable,
) {

    suspend operator fun invoke(
        trackId: Long,
        playlistId: Long,
        index: Int,
    ) = playlistWithTracksRepository.deleteTrackByIdAndPlaylistIdAndIndex(
        trackId = trackId,
        playlistId = playlistId,
        index = index,
    )
}
