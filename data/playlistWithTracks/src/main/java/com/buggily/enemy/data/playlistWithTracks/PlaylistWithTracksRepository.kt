package com.buggily.enemy.data.playlistWithTracks

import com.buggily.enemy.data.playlist.PlaylistRepositable
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable

class PlaylistWithTracksRepository(
    private val playlistRepository: PlaylistRepositable,
    private val playlistTrackRepository: PlaylistTrackRepositable,
) : PlaylistWithTracksRepositable {

    override suspend fun deletePlaylistByPlaylistId(
        playlistId: Long,
    ) {
        playlistRepository.deleteById(playlistId)
        playlistTrackRepository.deleteByPlaylistId(playlistId)
    }

    override suspend fun deleteTrackByIdAndPlaylistIdAndIndex(
        trackId: Long,
        playlistId: Long,
        index: Int,
    ) {
        playlistTrackRepository.deleteByIdAndPlaylistIdAndIndex(
            id = trackId,
            playlistId = playlistId,
            index = index,
        )

        playlistRepository.updateById(playlistId)
    }

    override suspend fun insertTrackByIdAndPlaylistId(
        trackId: Long,
        playlistId: Long,
    ) {
        playlistTrackRepository.insertByIdAndPlaylistId(
            id = trackId,
            playlistId = playlistId,
        )

        playlistRepository.updateById(playlistId)
    }
}
