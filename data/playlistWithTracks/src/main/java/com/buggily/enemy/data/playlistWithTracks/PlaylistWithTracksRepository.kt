package com.buggily.enemy.data.playlistWithTracks

import com.buggily.enemy.core.domain.GetInstantWithMetadata
import com.buggily.enemy.data.playlist.PlaylistRepositable
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable

class PlaylistWithTracksRepository(
    private val playlistRepository: PlaylistRepositable,
    private val playlistTrackRepository: PlaylistTrackRepositable,
    private val getInstantWithMetadata: GetInstantWithMetadata,
) : PlaylistWithTracksRepositable {

    override suspend fun deletePlaylistByPlaylistId(
        playlistId: Long,
    ) {
        playlistRepository.deleteById(playlistId)
        playlistTrackRepository.deleteByPlaylistId(playlistId)
    }

    override suspend fun deleteTrackByPlaylistId(
        playlistId: Long,
        track: TrackWithIndex,
    ) {
        playlistTrackRepository.deleteByPlaylistId(
            playlistId = playlistId,
            track = track,
        )

        playlistRepository.getById(playlistId)?.copy(
            modificationInstant = getInstantWithMetadata(),
        )?.let { playlistRepository.update(it) }
    }

    override suspend fun insertTrackByPlaylistId(
        playlistId: Long,
        track: Track,
    ) {
        playlistTrackRepository.insertByPlaylistId(
            playlistId = playlistId,
            track = track,
        )

        playlistRepository.getById(playlistId)?.copy(
            modificationInstant = getInstantWithMetadata(),
        )?.let { playlistRepository.update(it) }
    }
}
