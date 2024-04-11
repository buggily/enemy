package com.buggily.enemy.domain.playlistWithTracks

import com.buggily.enemy.data.playlistWithTracks.PlaylistWithTracksRepositable
import com.buggily.enemy.data.track.TrackWithIndex

class DeleteTrackByPlaylistId(
    private val playlistWithTracksRepository: PlaylistWithTracksRepositable,
) {

    suspend operator fun invoke(
        playlistId: Long,
        track: TrackWithIndex,
    ) = playlistWithTracksRepository.deleteTrackByPlaylistId(
        playlistId = playlistId,
        track = track,
    )
}
