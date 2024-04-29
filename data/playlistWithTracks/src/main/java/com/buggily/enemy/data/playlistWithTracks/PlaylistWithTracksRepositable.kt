package com.buggily.enemy.data.playlistWithTracks

interface PlaylistWithTracksRepositable {

    suspend fun deletePlaylistByPlaylistId(
        playlistId: Long,
    )

    suspend fun deleteTrackByIdAndPlaylistIdAndIndex(
        trackId: Long,
        playlistId: Long,
        index: Int,
    )

    suspend fun insertTrackByIdAndPlaylistId(
        trackId: Long,
        playlistId: Long,
    )
}