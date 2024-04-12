package com.buggily.enemy.data.playlistWithTracks

import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackWithIndex

interface PlaylistWithTracksRepositable {

    suspend fun deletePlaylistByPlaylistId(
        playlistId: Long,
    )

    suspend fun deleteTrackByPlaylistId(
        playlistId: Long,
        track: TrackWithIndex,
    )

    suspend fun insertTrackByPlaylistId(
        playlistId: Long,
        track: Track,
    )
}