package com.buggily.enemy.domain.playlistWithTracks

import com.buggily.enemy.data.playlistWithTracks.PlaylistWithTracksRepositable
import com.buggily.enemy.data.track.Track

class InsertTrackByPlaylistId(
    private val playlistWithTracksRepository: PlaylistWithTracksRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
        track: Track,
    ) = playlistWithTracksRepository.insertTrackByPlaylistId(
        playlistId = playlistId,
        track = track,
    )
}
