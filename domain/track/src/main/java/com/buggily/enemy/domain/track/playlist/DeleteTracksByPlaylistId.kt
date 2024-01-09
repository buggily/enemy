package com.buggily.enemy.domain.track.playlist


import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable

class DeleteTracksByPlaylistId(
    private val playlistTrackRepository: PlaylistTrackRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
    ) = playlistTrackRepository.deleteByPlaylistId(
        playlistId = playlistId,
    )
}
